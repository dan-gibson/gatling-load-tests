gatling-load-tests
==================

## Introduction

### [Gatling](http://gatling.io/)

Gatling is a Scala based open-source load test tool, that makes a break from 
traditional load-tools by utilising asynchronous concurrency and IO, and by using a major 
language (Scala) for its scripting.

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

## Real-time metrics

### Install Graphite 
```bash
# 0.9.x (stable) branch
git clone https://github.com/graphite-project/graphite-web.git
cd graphite-web
git checkout 0.9.x
cd ..
git clone https://github.com/graphite-project/carbon.git
cd carbon
git checkout 0.9.x
cd ..
git clone https://github.com/graphite-project/whisper.git
cd whisper
git checkout 0.9.x
sudo python setup.py install
cd ../carbon/
sudo python setup.py install
# configure carbon
cd /opt/graphite/conf
sudo cp carbon.conf.example carbon.conf
sudo cp storage-schemas.conf.example storage-schemas.conf

### Configure Gatling and Graphite
```config
data {
  writers = "console, file, graphite"
  reader = file

  graphite {
    host = "localhost"
    port = 2003
  }
}
```
### netcat
Use netcat to listen on port 2003 
``` 
nc -l 2003 
```

