picolib [![Build Status](https://travis-ci.com/ElectroStar/picolib.svg?branch=master)](https://travis-ci.com/ElectroStar/picolib) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.electrostar%3Apicolib&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.electrostar%3Apicolib) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.github.electrostar%3Apicolib&metric=coverage)](https://sonarcloud.io/dashboard?id=com.github.electrostar%3Apicolib) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.electrostar/picolib/badge.svg)](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A"com.github.electrostar"%20AND%20a%3A"picolib")[![Javadocs](https://www.javadoc.io/badge/com.github.electrostar/picolib.svg)](https://www.javadoc.io/doc/com.github.electrostar/picolib)
=========

Java Library to use Pico Technology's PicoScopes
---------------------
`picolib` provides an easy to use interface to interact with a PicoScope.

Links
-----

* [Releases](https://github.com/ElectroStar/picolib/releases)
* [Issue tracking](https://github.com/ElectroStar/picolib/issues)
* [Manufacturer](https://www.picotech.com/products/oscilloscope)

Prerequisites
--------
* IDE for Java like [NetBeans](https://netbeans.org/), [Elcipse](https://www.eclipse.org/), [IntelliJ](https://www.jetbrains.com/idea/) or equivalent IDE
* [Java SE Development Kit 8](https://www.oracle.com/technetwork/java/javase/overview/index.html) or later
* Installed PicoSDK driver from [Pico Technologies Downloads Page](https://www.picotech.com/downloads)

How to use
--------

`picolib` library can be found on `central` repository.

### Gradle

```groovy
dependencies {
    implementation 'com.github.electrostar:picolib:0.0.1'
}
```

### Maven
```xml
<dependency>
  <groupId>com.github.electrostar</groupId>
  <artifactId>picolib</artifactId>
  <version>0.0.1</version>
</dependency>
```

### Java
```java
ResultSet rs;
try(PicoScope ps = new PicoScope(UnitSeries.PICOSCOPE2000)) {
  ps.setChannel(Channel.CHANNEL_A, Coupling.DC, Range.RANGE_10V);
  ps.setTrigger(Channel.CHANNEL_A, TriggerDirection.FALLING, 2000f);
  ps.setTimebase(CollectionTime.DIV20MS);
  rs = ps.runBlock();
}
```
Implemented Features
--------
* ETS Mode
* Run and Streaming Mode
* Trigger
* Signal Generator

Limitations
-------
Currently only the `2000` Series is supported.

Contributing
-------
Contributions are welcome. Please refer to our [guidelines for contributing](CONTRIBUTING.md) for further information.

License
-------

Copyright 2018-2019 ElectroStar.

Licensed under the [GNU Lesser General Public License, Version 3.0](https://www.gnu.org/licenses/lgpl.txt)
