package bbc.sample

import scala.concurrent.duration._

import scala.util.matching.Regex

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Sample extends Simulation {

  val httpProtocol = http
    .baseURL("http://www.stage.bbc.co.uk")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
    .acceptHeader("""text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""")
    .acceptEncodingHeader("""gzip, deflate""")
    .acceptLanguageHeader("""en-gb,en;q=0.5""")
    .connection("""keep-alive""")
    .userAgentHeader("""Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0""")

     val scn = scenario("BBC")
      .group("BBC Group") {
       exec(http("sports-homepage").get("/sport/0/").check(status.is(200)))
       .exec(http("Weather").get("/weather/").check(status.is(200)))
       .exec(http("iPlayer").get("/iplayer").check(status.is(200)))
    }

   setUp(scn.inject(
        rampUsersPerSec(1) to(20) during(1 minute), 
        constantUsersPerSec(20) during(2 minutes)
      ).protocols(httpProtocol)) 
}
