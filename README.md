gatling-load-tests
==================

## Introduction

[Gatling](http://gatling.io/) is Scala based open-source load test tool, that makes a break from 
traditional load-tools by utilising asynchronous concurrency and IO, and by using a major 
language (Scala) as its scripting language.

## Installation

### Gatling
Use [gatling-sbt](https://github.com/gatling/gatling-sbt). 

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




