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
      
    val locCookie = csv("homepage/loc-cookies.csv").circular
    val locationId = csv("homepage/location-id.csv").circular
    val newsRegionId = csv("homepage/news-region-id.csv").circular

    val scn = scenario("HomePage")
        .feed(locCookie)
        .feed(locationId)
        .feed(newsRegionId)
        .exec(addCookie(Cookie("locserv", "${locCookie}")))
        .group("Domestic") { 
            exec(http("Domestic").get("""/""").check(status.is(200)))
            .exec(http("Location Id")
            .get("""/home/four/modules/h4weather/domestic/${locationId}/Trafford%2BPark/en-GB""").check(status.is(200))
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0"))
            .exec(http("News Region Id")
            .get("""/home/four/modules/h4discoveryzone/1/domestic/default/default/${newsRegionId}/en-GB.json""")
            .check(status.is(200))
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0"))
        }

      setUp(scn.inject(
          constantUsersPerSec(10) during(10 seconds)
      ).protocols(httpProtocol))
}




