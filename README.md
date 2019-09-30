# Axon Add-ons

[![Build Status](https://api.travis-ci.org/toolisticon/axon-addons.svg)](https://travis-ci.org/toolisticon/axon-addons)

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

## Links

* [Introducing axon-jgiven 0.1.1 to the world](https://groups.google.com/d/msg/axonframework/bcgVbfn8SME/7PtphIOkCQAJ)
