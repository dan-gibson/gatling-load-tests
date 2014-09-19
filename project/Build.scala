import io.gatling.sbt.GatlingPlugin

lazy val project = Project(...)
                     .enablePlugins(GatlingPlugin)
                     .settings(libraryDependencies ++= "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.0.0-RC5" % "test")
                     .settings(libraryDependencies ++= "io.gatling" % "test-framework" % "0.0-RC5" % "test")
