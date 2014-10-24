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

    val scn = scenario("Homepage")
        .feed(locCookie)
        .exec(addCookie(Cookie("locserv", "${locCookie}")))

        .randomSwitch(
            89.5d -> exec(http("Domestic").get("""/""").check(status.is(200))
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")),

            0.1d -> exec(http("Wales").get("""/wales/""").check(status.is(200))
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")),

            0.1d -> exec(http("Scotland").get("""/scotland/""").check(status.is(200))
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")),

            0.1d -> exec(http("northernireland").get("""/northernireland/""").check(status.is(200))
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")),

            0.1d -> exec(http("cymru").get("""/cymru/""").check(status.is(200))
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")), 

            0.1d -> exec(http("alba").get("""/alba/""").check(status.is(200))
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")),

            10d -> exec(http("Mobile").get("""/""").check(status.is(200))
            .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3"))
        )
       
        setUp(scn.inject(
          rampUsersPerSec(10) to(300) during(3 minutes),
          constantUsersPerSec(300) during(17 minutes)
        ).protocols(httpProtocol))
}




