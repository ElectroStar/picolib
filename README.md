picolib [![Build Status](https://travis-ci.com/ElectroStar/picolib.svg?branch=master)](https://travis-ci.com/ElectroStar/picolib) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.electrostar%3Apicolib&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.electrostar%3Apicolib)
=========

Java Library to use Pico Technology's PicoScopes
---------------------
`picolib` provides an easy to use interface to interact with a PicoScope.

Links
-----

* [Releases](https://github.com/ElectroStar/picolib/releases)
* [Java Doc](https://electrostar.github.io/picolib/apidocs/index.html)
* [Issue tracking](https://github.com/ElectroStar/picolib/issues)
* [Manufacturer](https://www.picotech.com/products/oscilloscope)

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
try(PicoScope ps = new PicoScope(UnitSeries.PicoScope2000er)) {
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

License
-------

Copyright 2018-2019 ElectroStar.

Licensed under the [GNU Lesser General Public License, Version 3.0](https://www.gnu.org/licenses/lgpl.txt)
