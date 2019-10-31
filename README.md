# Axon Framework Addons

[![Build Status](https://api.travis-ci.org/toolisticon/axon-addons.svg)](https://travis-ci.org/toolisticon/axon-addons)
[![sponsored](https://img.shields.io/badge/sponsoredBy-Holisticon-RED.svg)](https://holisticon.de/)
[![release](https://img.shields.io/badge/jcenter-0.1.1-BLUE.svg)](https://bintray.com/toolisticon/maven/axon-addons/)

Add-ons for Axon Framework provides a set of libraries extending the framework with useful tools.

## Available Add-Ons

* axon-jgiven: an add-on fostering the usage of JGiven Test framework for writing Axon Aggregate 
and Axon Saga Fixture-based tests.


## Installation

We are distributing the artifact using Bintray JCenter. In order to use it, you have to add the 
repository.

### Using Apache Maven

Add the repository to your pom.
```
<repositories>
    <repository>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <id>jcenter</id>
        <name>bintray</name>
        <url>https://jcenter.bintray.com</url>
    </repository>
</repositories>
```
and just use the library:
``` 
<dependency>
    <groupId>io.toolisticon.addons.axon</groupId>
    <artifactId>axon-jgiven</artifactId>
    <version>0.1.1</version>
    <scope>test</scope>
</dependency>
```

## Release Notes

### 4.2.0-0.2

* change license to [Apache 2](./LICENSE)
* upgrade gradle [5.6.3](https://docs.gradle.org/5.6.3/release-notes.html)

## Links

* [Introducing axon-jgiven 0.1.1 to the world](https://groups.google.com/d/msg/axonframework/bcgVbfn8SME/7PtphIOkCQAJ)


## Maintainer

* [Jan Galinski](https://github.com/jangalinski)
* [Simon Zambrovski](https://github.com/zambrovski)
