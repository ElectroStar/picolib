#!/usr/bin/env bash
set -euo pipefail

# Tool Dir
TOOL_DIR="${HOME}/tools"
TOOL_BIN_DIR="${TOOL_DIR}/bin"
LIB_NAME="picolib"

# Github Release Tool
GHR_ARCH="linux_amd64"
GHR_VERSION="v0.12.1"
GHR_URL="https://github.com/tcnksm/ghr/releases/download"
GHR_NAME="ghr_${GHR_VERSION}_${GHR_ARCH}"
GHR_TOOL="$TOOL_BIN_DIR/ghr"

# Changelog Generator
CHG_TOOL_OUTPUT_FILE="RELEASE_CHANGELOG.md"
CHG_TOOL_OPTS_ADD_TAG="--since-tag"
CHG_TOOL_OPTS="--output $CHG_TOOL_OUTPUT_FILE --no-verbose --exclude-tags-regex .*SNAPSHOT.*"
CHG_TOOL_NAME="github_changelog_generator"
CHG_TOOL="$TOOL_BIN_DIR/$CHG_TOOL_NAME"

# Maven Options
MAVEN_PREVENT_RECOMPILE="-Dmaven.compiler.useIncrementalCompilation=false"
MAVEN_CLI_OPTS="--batch-mode --errors --fail-fast --show-version $MAVEN_PREVENT_RECOMPILE"

if [[ -z ${TRAVIS_BRANCH+x} ]]; then TRAVIS_BRANCH=""; fi

#
# Travis fails on timeout when build does not print logs
# during 10 minutes. This aims to bypass this
# behavior when building the slow sonar-server sub-project.
#
keep_alive() {
  while true; do
    echo -en "\a"
    sleep 60
  done
}
keep_alive &

#
# Get the Last Tag if possible
#
getLastTag() {
  echo "Determine last TAG"
  if [ ${TRAVIS_TAG} ]; then
    export LAST_TAG=$(git describe --match "[0-9]*.[0-9]*.*[0-9]" --tags --abbrev=0 $TRAVIS_TAG^)
    if [ "$LAST_TAG" ]; then
      if [ "CHG_TOOL_OPTS_ADD_TAG" ]; then
        export CHG_TOOL_OPTS="$CHG_TOOL_OPTS $CHG_TOOL_OPTS_ADD_TAG $LAST_TAG"
      fi
    fi
  else
    export LAST_TAG=""
  fi
  if [ ${LAST_TAG} ]; then
    echo "Last Tag is $LAST_TAG"
  else
    echo "No Last Tag detected"
  fi
}

#
# Check Tool Directory
#
checkToolDir() {
  if ! [ -d ${TOOL_BIN_DIR} ]; then
    mkdir -p ${TOOL_BIN_DIR}
  fi
}

#
# Install ghr
#
installGHR() {
  # Check Tool Dir
  checkToolDir
  
  # Check for new Version
  if [ -f "$GHR_TOOL" ]; then
    INSTALLED_VERSION=$($GHR_TOOL -version | grep -P 'v\d[a-zA-Z0-9\.\-_]+' -o)
    if [ "$INSTALLED_VERSION" ] && [ "$INSTALLED_VERSION" != "$GHR_VERSION" ]; then
      echo "Another GHR Version found. Remove Version $INSTALLED_VERSION"
      rm $GHR_TOOL
    fi
  fi

  # Finaly check if the tool is installed and if not install it
  if [ ! -f "$GHR_TOOL" ]; then
    echo "GHR not installed"
    echo "Downloading and installing GHR $GHR_VERSION for $GHR_ARCH"
    wget $GHR_URL/$GHR_VERSION/$GHR_NAME.tar.gz -P $HOME/ -nv
    tar -xzf ${HOME}/${GHR_NAME}.tar.gz -C ${TOOL_BIN_DIR}/ --strip-components 1 ${GHR_NAME}/ghr
    rm ${HOME}/${GHR_NAME}.tar.gz
    if [ ! -f "$GHR_TOOL" ]; then
      echo "Could not install GHR"
      exit 1
    else
      echo "GHR successfully installed"
    fi
  else
    echo "GHR installed"
  fi
}

#
# Install Changelog Tool
#
installChangelog() {
  # Check Tool Dir
  checkToolDir

  # Check if the tool is installed and if not install it
  if ! [ -f "$CHG_TOOL" ]; then
    echo "Installing Changelog Tool"
    gem install $CHG_TOOL_NAME --install-dir $TOOL_DIR
    if ! [ -f "$CHG_TOOL" ]; then
      echo "Could not install Changelog Tool"
      exit 1
    else
      echo "Changelog Tool succesfully installed"
    fi
  else
    echo "Changelog Tool installed"
  fi
  export GEM_HOME="$TOOL_DIR"
}

#
# Installing the Tools
#
installTools() {
  echo "Checking and Installing the Tools"
  installGHR
  installChangelog
}

#
# Compile the Sources
#
compileSources() {
  echo "Compile Sources"
  mvn $MAVEN_CLI_OPTS  clean compile -DskipTests=true
}

#
# Run Tests
#
runTests() {
  echo "Run Tests"
  mvn $MAVEN_CLI_OPTS test -Pjacoco
}

#
# Check Code Quality
#
checkCodeQuality() {
  echo "Checking Code Quality with Sonar"
  mvn $MAVEN_CLI_OPTS sonar:sonar -s ci_settings.xml
}

#
# Run Compile and Tests
#
testBuild() {
  compileSources
  runTests
  checkCodeQuality
}

#
# Check if the Version is current with the Tag
#
checkVersion() {
  echo "Checking Version"
  mvn help:evaluate -N -Dexpression=project.version > /dev/null
  export PROJECT_VERSION=$(mvn help:evaluate -N -Dexpression=project.version|grep -v '\[')
  echo "Found Version ${PROJECT_VERSION}"

  # Check for SNAPSHOT
  if [[ ${PROJECT_VERSION} =~ "SNAPSHOT" ]]; then
    export IS_SNAPSHOT=1
    echo "Version is a Snapshot"
  else
    export IS_SNAPSHOT=0
  fi
  
  # Execute the Check only if it is not a SNAPSHOT
  if [ ${IS_SNAPSHOT} -eq 0 ]; then
    if ! [ ${PROJECT_VERSION} = ${TRAVIS_TAG} ]; then
      if [ ${TRAVIS_TAG} ]; then
        echo "Expected Version ${TRAVIS_TAG} but found Version ${PROJECT_VERSION} in pom.xml"
      else
        echo "Current Version ${PROJECT_VERSION} is not a SNAPSHOT and no Tag is given."
      fi
      exit 1
    fi
  fi
}

function version_gt() {
  if [[ ! -z "$(sort --help | { grep GNU || true; })" ]]; then
    test "$(printf '%s\n' "$@" | sort -V | head -n 1)" != "$1";
  else
    test "$(printf '%s\n' "$@" | sort | head -n 1)" != "$1";
  fi
}

#
# GPG Fix from mave-build project
# https://github.com/ci-and-cd/maven-build/blob/develop/src/main/ci-script/lib_ci.sh
#
importGPG() {
  # GPG Options
  if which gpg2 > /dev/null; then CI_OPT_GPG_EXECUTABLE="gpg2"; elif which gpg > /dev/null; then CI_OPT_GPG_EXECUTABLE="gpg"; else CI_OPT_GPG_EXECUTABLE=""; fi

  local gpg_cmd=""
  echo "determine gpg or gpg2 to use"
  # invalid option --pinentry-mode loopback
  if which gpg2 > /dev/null; then
    gpg_cmd="gpg2 --use-agent"
  elif which gpg > /dev/null; then
    gpg_cmd="gpg"
  fi

  if [[ -n "${CI_OPT_GPG_EXECUTABLE}" ]]; then
    echo "using ${CI_OPT_GPG_EXECUTABLE}"
    GPG_TTY=$(tty || echo "")
    if [[ -z "${GPG_TTY}" ]]; then unset GPG_TTY; fi
    echo "gpg tty '${GPG_TTY}'"

    # use --batch=true to avoid 'gpg tty not a tty' error
    ${gpg_cmd} --batch=true --version

    # config gpg (version > 2.1)
    if version_gt $(${CI_OPT_GPG_EXECUTABLE} --batch=true --version | { grep -E '[0-9]+\.[0-9]+\.[0-9]+' || true; } | head -n1 | awk '{print $NF}') "2.1"; then
      echo "gpg version greater than 2.1"
      mkdir -p ~/.gnupg && chmod 700 ~/.gnupg
      touch ~/.gnupg/gpg.conf
      echo "add 'use-agent' to '~/.gnupg/gpg.conf'"
      echo 'use-agent' > ~/.gnupg/gpg.conf
      if version_gt $(${CI_OPT_GPG_EXECUTABLE} --batch=true --version | { grep -E '[0-9]+\.[0-9]+\.[0-9]+' || true; } | head -n1 | awk '{print $NF}') "2.2"; then
        # on gpg-2.1.11 'pinentry-mode loopback' is invalid option
        echo "add 'pinentry-mode loopback' to '~/.gnupg/gpg.conf'"
        echo 'pinentry-mode loopback' >> ~/.gnupg/gpg.conf
      fi
      cat ~/.gnupg/gpg.conf
      #gpg_cmd="${gpg_cmd} --pinentry-mode loopback"
      #export GPG_OPTS='--pinentry-mode loopback'
      #echo GPG_OPTS: ${GPG_OPTS}
      echo "add 'allow-loopback-pinentry' to '~/.gnupg/gpg-agent.conf'"
      touch ~/.gnupg/gpg-agent.conf
      echo 'allow-loopback-pinentry' > ~/.gnupg/gpg-agent.conf
      cat ~/.gnupg/gpg-agent.conf
      echo restart the agent
      echo RELOADAGENT | gpg-connect-agent
    fi
    echo ${GPG_SECRET_KEYS} | base64 --decode | ${gpg_cmd} --import
  fi
}

#
#
#
mavenDeploy() {
  echo "Deploying Jars"
  mvn $MAVEN_CLI_OPTS deploy site -s ci_settings.xml -Pjacoco,deploy -DskipTests=true
}

#
# Create Changelog
#
createChangelog() {
  getLastTag
  if [ ${LAST_TAG} ]; then
    echo "Creating Changelog"
    if ! [ -z ${GH_TOKEN+x} ]; then CHG_TOOL_OPTS="$CHG_TOOL_OPTS --token $GH_TOKEN"; fi
    $CHG_TOOL $CHG_TOOL_OPTS
    export GOT_CHANGELOG=1
  else
    export GOT_CHANGELOG=0
  fi
}

#
# Copy Release Files
#
copyReleaseFiles() {
  # Create Release Folder
  if ! [ -d "release" ]; then
    mkdir release
  fi
  # Copy Project Library Jars
  cp target/${LIB_NAME}*.jar release/
}

#
# Create Github Release
#
createGitHubRelease() {
  echo "Creating GitHub Release"
  if [ ${GOT_CHANGELOG} -eq 1 ]; then
    echo "Got Changelog for release"
    BODY=$(<${CHG_TOOL_OUTPUT_FILE})
  else
    echo "No Changelog for release"
    BODY=""
  fi
  
  # Copy Release Files
  copyReleaseFiles

  # Finaly create the Release and Upload the Files
  GHR_OPTS="-t ${GH_TOKEN} -n ${TRAVIS_TAG} -replace"
  if [ ${IS_SNAPSHOT} -eq 1 ]; then
    GHR_OPTS="${GHR_OPTS} -prerelease"
  fi
  if [ ${BODY} ]; then
    ${GHR_TOOL} ${GHR_OPTS} \
      -b "${BODY}" \
      ${TRAVIS_TAG} release/
  else
    ${GHR_TOOL} ${GHR_OPTS} \
      ${TRAVIS_TAG} release/
  fi

  rm -rf release/
  if [ -f ${CHG_TOOL_OUTPUT_FILE} ]; then
    rm ${CHG_TOOL_OUTPUT_FILE}
  fi
}

setupGit() {
  git config --global user.email "travis@travis-ci.com"
  git config --global user.name "Travis CI"
}

#
# Update GH-Pages
#
updateGHPages() {
  # Determine if gh-pages branch already exists
  git rev-parse --verify gh-pages || failed=$?
  # Setup Git Config for Commit
  setupGit
  if [ -z ${failed+x} ]; then
    git checkout gh-pages
  else 
    git checkout --orphan gh-pages
  fi
  git rm -rf .
  if [ -d src ]; then
    rm -rf src
  fi
  cp -r target/site/* .
  rm -rf target
  git add .
  git commit --message "Travis build for ${TRAVIS_TAG}"
  git remote add origin-pages https://${GH_TOKEN}@github.com/ElectroStar/picolib.git > /dev/null 2>&1
  git push --quiet --set-upstream origin-pages gh-pages
}

#
# Deploy
#
deploy() {
  echo "Start Deploying Procedure"
  echo "Branch: ${TRAVIS_BRANCH}"
  echo "Tag: ${TRAVIS_TAG}"
  if [ ${TRAVIS_BRANCH} = "master" ] || [ ${TRAVIS_TAG} ]; then
    # Check and Extract the Project Version
    checkVersion
    # Import GPG for Signing
    importGPG  
    # Check for SNAPSHOT
    if [ ${IS_SNAPSHOT} -eq 1 ]; then
      # Deploy SNAPSHOT
      mavenDeploy      
    else
      # Check the TAG is present if it is not a SNAPSHOT
      if [ ${TRAVIS_TAG} ]; then 
        # Deploy Release
        mavenDeploy
        installTools
        createChangelog
        createGitHubRelease
        updateGHPages
      fi
    fi
  fi
}

testBuild
deploy
