gatling-load-tests
==================

## Introduction

### [Gatling](http://gatling.io/)

Gatling is Scala based open-source load test tool, that makes a break from 
traditional load-tools by utilising asynchronous concurrency and IO, and by using a major 
language (Scala) as its scripting language.

### [SBT](http://www.scala-sbt.org/)
SBT is a build tool that has become the de-facto build tool for Scala projects.

## Installation 
### SBT
```bash
$ wget "https://dl.bintray.com/sbt/rpm/sbt-0.13.6.rpm"
$ sudo yum install -y "sbt-0.13.6.rpm"
```

### Test Resources
```bash 
$ git clone git@github.com:BBC/gatling-load-tests.git
```

## Execution
```bash 
$ cd gatling-load-tests
$ sbt
> testOnly *NewsSample*
```




