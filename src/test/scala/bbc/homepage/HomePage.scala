package bbc.homepage

import scala.concurrent.duration._

import scala.util.matching.Regex

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HomePage extends Simulation {

  val httpProtocol = http
    .baseURL("http://www.stage.bbc.co.uk")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
    .acceptHeader("""text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""")
    .acceptEncodingHeader("""gzip, deflate""")
    .acceptLanguageHeader("""en-gb,en;q=0.5""")
    .connection("""keep-alive""")
    .userAgentHeader("""Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0""")

      
    val locCookieData = csv("loc-cookies.csv").circular
    def parseId(str: String) = "[0-9]{7}".r findFirstMatchIn str mkString

    val scn = scenario("HomePage")
      .feed(locCookieData)
      .exec(http("homepage")
      .get("""/"""))

      .exec(http("location id")
      .get("/home/four/modules/h4weather/domestic/" + parseId("${locCookie}") + "/Trafford%2BPark/en-GB"))

    setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
