#!/usr/bin/env bash
set -euo pipefail

# Regex for Version Detection
RELEASE_VERSION_REGEX="^[0-9]+\.[0-9]+\.[0-9]+$"
RC_VERSION_REGEX="^[0-9]+\.[0-9]+\.[0-9]+-RC[0-9]+$"
SNAPSHOT_VERSION_REGEX="^[0-9]+\.[0-9]+\.[0-9]+-SNAPSHOT$"

# Tool Dir
TOOL_DIR="${HOME}/tools"
TOOL_BIN_DIR="${TOOL_DIR}/bin"
LIB_NAME="picolib"
REPO_SLUG="ElectroStar/${LIB_NAME}"

# Github Release Tool
GHR_ARCH="linux_amd64"
GHR_VERSION="v0.12.1"
GHR_URL="https://github.com/tcnksm/ghr/releases/download"
GHR_NAME="ghr_${GHR_VERSION}_${GHR_ARCH}"
GHR_TOOL="$TOOL_BIN_DIR/ghr"

# Changelog Generator
CHG_TOOL_OUTPUT_FILE="RELEASE_CHANGELOG.md"
CHG_TOOL_OPTS="--output $CHG_TOOL_OUTPUT_FILE --no-verbose"
CHG_TOOL_EXCLUDE_REGEX="${SNAPSHOT_VERSION_REGEX}"
CHG_TOOL_NAME="github_changelog_generator"
CHG_TOOL="$TOOL_BIN_DIR/$CHG_TOOL_NAME"

# Maven Options
MAVEN_PREVENT_RECOMPILE="-Dmaven.compiler.useIncrementalCompilation=false"
MAVEN_CLI_OPTS="--batch-mode --errors --fail-fast --show-version $MAVEN_PREVENT_RECOMPILE"

if [[ -z ${TRAVIS_BRANCH+x} ]]; then TRAVIS_BRANCH=""; fi
if [[ -z ${TRAVIS_TAG+x} ]]; then TRAVIS_TAG=""; fi
if [[ -z ${TRAVIS_COMMIT_MESSAGE+x} ]]; then TRAVIS_COMMIT_MESSAGE=""; fi
COMMIT_SUBJECT=$(echo ${TRAVIS_COMMIT_MESSAGE} | head -n1)

#
# Help Function to check if it matches a regex
# First Param is the value to check
# Second Param is the regular expression
#
function matchRegex() {
  test "$(printf '%s' "${1}" | { grep -P "${2}" || true; })";
}

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
  if [ ${IS_RC} ]; then
    versions=$(git tag -l | grep -P "(${RELEASE_VERSION_REGEX}|${RC_VERSION_REGEX})" | sed -e "s/-RC/~RC/g" | sort -Vr | sed -e "s/~RC/-RC/g")
  else
    versions=$(git tag -l | grep -P "${RELEASE_VERSION_REGEX}" | sort -Vr)
  fi
  export LAST_TAG=$(printf '%s\n' "${versions}" | { grep -A1 "${TRAVIS_TAG}" || true; } | { grep -v "${TRAVIS_TAG}" || true; })
  
  if [ ${LAST_TAG} ]; then
    echo "The previous Version before ${PROJECT_VERSION} is $LAST_TAG"
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
  # Only process when it is no pull request and if it is a pull request only accept pull requests for the same repo
  if [ ${TRAVIS_PULL_REQUEST} = "false" ] || [ ${TRAVIS_PULL_REQUEST_SLUG} = ${REPO_SLUG} ]; then
    # Additional Options
    sonar_options=""

    # For Pull Request
    if [ ${TRAVIS_PULL_REQUEST} != "false" ]; then
      echo "Checking Code Quality with Sonar for Pull Request"  
      sonar_options="-Dsonar.pullrequest.key=${TRAVIS_PULL_REQUEST} -Dsonar.pullrequest.branch=${TRAVIS_PULL_REQUEST_BRANCH} -Dsonar.pullrequest.base=${TRAVIS_BRANCH} ${sonar_options}"
    else
      if [ ${TRAVIS_BRANCH} != "master" ]; then
        # Determinate the target branch
        target_branch="develop"
        if [ ${TRAVIS_BRANCH} = "develop" ]; then
          target_branch="master"
        elif matchRegex ${TRAVIS_BRANCH} "^hotfix.*"; then
          target_branch="master"
        elif matchRegex ${TRAVIS_BRANCH} "^release.*"; then
          target_branch="master"
        fi
        sonar_options="-Dsonar.branch.name=${TRAVIS_BRANCH} -Dsonar.branch.target=${target_branch} ${sonar_options}"
        echo "Checking Code Quality with Sonar for branch ${TRAVIS_BRANCH} targetting branch ${target_branch}"
      else
        echo "Checking Code Quality with Sonar for branch ${TRAVIS_BRANCH}"
      fi
    fi

    mvn $MAVEN_CLI_OPTS sonar:sonar -s ci_settings.xml ${sonar_options}
  fi
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
  echo "Found Version ${PROJECT_VERSION} in pom.xml"

  export IS_RC=""
  export IS_RELEASE=""
  if [ ${TRAVIS_TAG} ]; then
    # Check if it is a Release
    if matchRegex ${TRAVIS_TAG} ${RELEASE_VERSION_REGEX}; then
      export IS_RELEASE="1"
      export TRAVIS_BRANCH="master"
    fi
    # Check for Release Candidate
    if matchRegex ${TRAVIS_TAG} ${RC_VERSION_REGEX}; then 
      export IS_RC="1"
      export TRAVIS_BRANCH="release-$(echo ${TRAVIS_TAG} | sed -e 's/-RC[0-9]*//g')"
    fi
  fi
  # Check for Snapshot
  if matchRegex ${PROJECT_VERSION} ${SNAPSHOT_VERSION_REGEX} && [ ${TRAVIS_BRANCH} = "develop" ]; then
    IS_SNAPSHOT="1"
  else
   IS_SNAPSHOT=""
  fi
  
  # If it is a release or release candidate check the project version against the version in tag
  if [ ${IS_RELEASE} ] || [ ${IS_RC} ]; then
    if ! [ ${PROJECT_VERSION} = ${TRAVIS_TAG} ]; then
     echo "Expected Version ${TRAVIS_TAG} but found Version ${PROJECT_VERSION} in pom.xml"
     exit 1
    fi
  fi

  echo ""
  echo ""
  echo "=================================================================================================="
  echo "Information about this Build:"
  echo "Version: ${PROJECT_VERSION}"
  if [ ${TRAVIS_TAG} ]; then
    echo "Tag: ${TRAVIS_TAG}"
  else
    echo "Branch: ${TRAVIS_BRANCH}"
  fi
  if [ ! ${IS_RELEASE} ] && [ ! ${IS_RC} ] && [ ! ${IS_SNAPSHOT} ]; then
    echo "Build Type is default."
    echo "Only Continuous Integration Tools will be executed!"
  else
    if [ ${IS_RELEASE} ]; then
      echo "Build Type is a Release."
    elif [ ${IS_RC} ]; then
      echo "Build Type is a Release Candidate."
    elif [ ${IS_SNAPSHOT} ]; then
      echo "Build Type is a Snapshot."
    fi
  fi
  if [ ${TRAVIS_PULL_REQUEST} != "false" ]; then
    echo "Pull Request for RP #${TRAVIS_PULL_REQUEST}:"
    echo " from branch ${TRAVIS_PULL_REQUEST_BRANCH} of repo ${TRAVIS_PULL_REQUEST_SLUG} to ${TRAVIS_BRANCH}" 
  fi
  echo "Repository: ${TRAVIS_REPO_SLUG}"
  echo "=================================================================================================="
  echo ""
  echo ""
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
    export CHG_TOOL_OPTS="${CHG_TOOL_OPTS} --since-tag $LAST_TAG"
    if ! [ -z ${GH_TOKEN+x} ]; then CHG_TOOL_OPTS="$CHG_TOOL_OPTS --token $GH_TOKEN"; fi
    if [ ${IS_RELEASE} ]; then
      export CHG_TOOL_EXCLUDE_REGEX="(${CHG_TOOL_EXCLUDE_REGEX}|${RC_VERSION_REGEX})"
    fi
    export CHG_TOOL_OPTS="${CHG_TOOL_OPTS} --exclude-tags-regex ${CHG_TOOL_EXCLUDE_REGEX}"
    echo "Creating Changelog"
    $CHG_TOOL $CHG_TOOL_OPTS
    export GOT_CHANGELOG="1"
  else
    export GOT_CHANGELOG=""
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
  if [ ${GOT_CHANGELOG} ]; then
    echo "Got Changelog for release"
    BODY=$(<${CHG_TOOL_OUTPUT_FILE})
  else
    echo "No Changelog for release provided"
    BODY=""
  fi
  
  # Copy Release Files
  copyReleaseFiles

  # Finaly create the Release and Upload the Files
  GHR_OPTS="-t ${GH_TOKEN} -n ${TRAVIS_TAG} -replace"
  if [ ${IS_RC} ]; then
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

#
# Update GH-Pages
#
updateGHPages() {
  # Setup Git Config for Commit
  git config --global user.email "travis@travis-ci.com"
  git config --global user.name "Travis CI"

  # Determine if gh-pages branch already exists
  if [ $(git rev-parse --verify gh-pages || true;) ]; then
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
  # No Deploy for Pull Requests
  if [ ${TRAVIS_PULL_REQUEST} = "false" ]; then
    # Only Deploy for this repo
    if [ ${TRAVIS_REPO_SLUG} = ${REPO_SLUG} ]; then
      # Only Deploy when Release, Release Candidate or Snapshot
      if [ ${IS_RELEASE} ] || [ ${IS_RC} ] || [ ${IS_SNAPSHOT} ]; then
        # Import GPG for Signing
        importGPG  
        # Deploy to Central
        mavenDeploy

        # Continue only for Releases and Release Candidates
        if [ ${IS_RELEASE} ] || [ ${IS_RC} ]; then
          # Install Deploy Tools for Changelog and Github Releases
          installTools
          # Create Changelog
          createChangelog
          # Create Github Release
          createGitHubRelease    

          # Update of the GH-Pages only for Releases
          if [ ${IS_RELEASE} ]; then
            updateGHPages       
          fi
        fi
      fi
    fi
  fi
}

# Check if this commit should be skipped
if [ ${COMMIT_SUBJECT} ]; then
  if matchRegex ${COMMIT_SUBJECT} ".*\[skip ci\].*" then 
    echo "Commit Subject contains [skip ci] and the ci job will be skipped!"
    exit 0
  fi
fi

checkVersion
testBuild
deploy
