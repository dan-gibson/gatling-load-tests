name := "BBC Gatling Load Tests"

version := "0.0.1"

val test = project.in(file("."))
  .enablePlugins(GatlingPlugin)
    .settings(libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.0.0-RC5" % "test")
    .settings(libraryDependencies += "io.gatling" % "test-framework" % "1.0-RC5" % "test")

