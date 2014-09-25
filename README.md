gatling-load-tests
==================

## Gatling

[Gatling](http://gatling.io/) is a high performance load test tool based on Scala, Akka and Netty. 

For me, the two most interesting aspects of Gatling are: 

1. Asynchronous concurrency/IO
2. Tests as code

The benefits of asynchronous concurrency/IO are that it is far more effecient than the one thread per user model that is 
prevalent in other load tools. Also, instead of using a GUI and/or XML for test scripts, Gatling uses a fairly easily 
understandable Scala DSL. This allows us to version control our scripts and have them be reviewed without having to 
import them into a tool. We also can use the full power of the Scala language if we want to verify response bodies, 
parse input files or whatever else. It also makes the activity of load test scripting, pleasurable.

## Installation

I would suggest using [gatling-sbt](https://github.com/gatling/gatling-sbt). You will need the version 0.13.6 of SBT to allow
auto-enabling of plugins. Add the below to your project/build.properties file.

```scala
sbt.version=0.13.6
```

## Contact

Twitter: @AidyLewis  
BBC IRC: #loadtest
