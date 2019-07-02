# Contributing to picolib

:+1::tada: First off, thanks for taking the time to contribute! :tada::+1:

The following is a set of guidelines for contributing to picolib, which is hosted on GitHub. These are mostly guidelines, not rules. Use your best judgment, and feel free to propose changes to this document in a pull request.

#### Table Of Contents

[Code of Conduct](#code-of-conduct)
[How Can I Contribute?](#how-can-i-contribute)
  * [Reporting Bugs](#reporting-bugs)
  * [Getting Started](#getting-started)
  * [Create the working (supporting) branch](#create-the-working-(supporting)-branch)
  * [Code](#code)
  * [Commit](#commit)
  * [Submit](#submit)
 
[Styleguides](#styleguides)
  * [Git Commit Messages](#git-commit-messages)
  * [File Headers](#file-headers)
  * [Java Styleguide](#javascript-styleguide)

## Code of Conduct

This project and everyone participating in it is governed by the [Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code. Please report unacceptable behavior.

## How Can I Contribute?

### Reporting Bugs

This section guides you through submitting a bug report for picolib. Following these guidelines helps maintainers and the community understand your report, reproduce the behavior, and find related reports.

Before creating bug reports, please check if the same bug has already been reported and if there is a workaround that solves your problem. When you are creating a bug report, please include as many details as possible. Fill out [the required template](https://github.com/ElectroStar/picolib/blob/master/.github/ISSUE_TEMPLATE/BUG_REPORT.md), the information it asks for helps us resolve issues faster.

> **Note:** If you find a **Closed** issue that seems like it is the same thing that you're experiencing, open a new issue and include a link to the original issue in the body of your new one.

### Getting Started

If you are just getting started with Git, GitHub and/or contributing to Hibernate via
GitHub there are a few pre-requisite steps to follow:

* Make sure you have a [GitHub account](https://github.com/signup/free)
* [Fork](https://help.github.com/articles/fork-a-repo) the picolib repository.  As discussed in
the linked page, this also includes:
    * [Set](https://help.github.com/articles/set-up-git) up your local git install
    * Clone your fork

### Create the working (supporting) branch

Create a [supporting branch](https://nvie.com/posts/a-successful-git-branching-model/#supporting-branches) 
on which you will work.  The convention is to indicate if it is a feature, a hotfix or a release and what it is about.


_If there is not already a issue covering the work you want to do, create one._
  
Assuming you will be working on a new feature called `myfeature` use the following command: `git checkout -b feature-myfeature develop`


### Code

Do yo thing and please follow our [Styleguide for Java](#java-styleguide)


### Commit
* Make commits of logical units.
* Be sure to use the issue id in the commit message.  This is how github will pick
up the related commits and display them on the issue.
* Follow the Styleguide for [Git Commit Messages](#git-commit-messages)
* Make sure you have added the necessary tests for your changes.
* Run _all_ the tests to assure nothing else was accidentally broken.
* Make sure your source does not violate the checkstyles.

_Prior to committing, if you want to pull in the latest upstream changes (highly appreciated btw), please use rebasing rather than merging.  Merging creates "merge commits" that really muck up the project timeline._

## Submit

* Push your changes to the supporting branch in your fork of the repository.
* Initiate a [pull request](http://help.github.com/articles/creating-a-pull-request)
* Update the issue, adding a comment including a link to the created pull request
	_if the issue id was not used in the commit message_.


It is important that this supporting branch on your fork:

* be isolated to just the work on this one issue, or multiple issues if they are
	related and also fixed/implemented by this work.  The main point is to not push
	commits for more than one PR to a single branch - GitHub PRs are linked to
	a branch rather than specific commits.
* remain until the PR is closed.  Once the underlying branch is deleted the corresponding
	PR will be closed, if not already, and the changes will be lost.

## Styleguides

### Git Commit Messages

Please format commit messages as follows (based on [A Note About Git Commit Messages](http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html)):

```
Summarize change in 50 characters or less

Provide more detail after the first line. Leave one blank line below the
summary and wrap all lines at 72 characters or less.

If the change resolves an issue, leave another blank line after the final
paragraph and indicate which issue is resolved in the specific format
below.

Resolves: #123
See also: #456, #789
```

Also do your best to factor commits appropriately, not too large with unrelated things in the same commit, and not too small with the same small change applied N times in N different commits. You can also include `[skip ci]` in the commit title when changing documentation only.


### File Headers

The following file header is the used for picolib. Please use it for new files.

```
/* 
 * picolib, open source library to work with PicoScopes.
 * Copyright (C) 2018-2019 ElectroStar and contributors
 *
 * This file is part of picolib.
 *
 * picolib is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 *  version 3 of the License, or (at your option) any later version.
 *
 * picolib is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with picolib. If not, see <http://www.gnu.org/licenses/>.
 */
```

- See [HEADER](HEADER) for a netbeans template.

### Java Styleguide

All Java files must adhere to [Google Java Style](https://google.github.io/styleguide/javaguide.html).  The only exception to these rules applies to all files in package `com.github.electrostar.picolib.library`. Within this package, all methods and parameters must exactly match the function names and parameter names from the header files.

The most importent settings in your IDE are:

- Number of Spaces per Indent: 2
- Tab Size: 2
- Right Margin: 100


## Contributing Device Integration

We currently missing the integration of Pico Technology PicoScope devices. You may be interested in one of the following integrations:

Series:

- 2000A
- 3000
- 3000A
- 4000
- 4000A
- 5000A
- 6000
- 9200
- 9300

See the offical library documentation for the different series [here](https://www.picotech.com/library/documentation).
