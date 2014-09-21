package wwfeatures

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GeoIpDetection extends Simulation {

  val httpProtocol = http
    .baseURL("http://www.stage.bbc.com")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
    .acceptHeader("""text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""")
    .acceptEncodingHeader("""gzip, deflate""")
    .acceptLanguageHeader("""en-gb,en;q=0.5""")
    .connection("""keep-alive""")
    .userAgentHeader("""Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0""")

    val scn = scenario("Earth")
      .exec(addCookie(Cookie("ForgeWWCVFCountryCode", "us")))
      .exec(http("earth").get("""/"""))

    setUp(scn.inject(rampUsers(10) over(5.seconds)).protocols(httpProtocol))
}
