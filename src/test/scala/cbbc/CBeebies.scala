package cbbc

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CBeebies extends Simulation {

     val httpProtocol = http
       .baseURL("http://www.stage.bbc.co.uk/cbeebies")
       .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.svg""", """.*\.png"""), WhiteList())
       .acceptHeader("""text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""")
       .acceptEncodingHeader("""gzip, deflate""")
       .acceptLanguageHeader("""en-gb,en;q=0.5""")
       .connection("""keep-alive""")

      val cbbcUrls = csv("cbbc-urls-updated-29-09-1119.csv").circular

      val scn = scenario("CBBC Nitro")
        .feed(cbbcUrls)
       
        .randomSwitch(
          60d -> exec(addCookie(Cookie("ckps_d", "d")))
            .exec(http("Desktop")
            .get("${cbbcUrl}")
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")
            .check(status.is(200))),

          8d -> exec(addCookie(Cookie("ckps_d", "d")))
            .exec(http("iPhone 4S")
            .get("${cbbcUrl}")
            .header("User-Agent", 
                  "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5")
            .check(status.is(200))),

          8d -> exec(addCookie(Cookie("ckps_d", "d")))
            .exec(http("Samsung Galaxy S2")
            .get("${cbbcUrl}")
            .header("User-Agent", 
                  "Mozilla/5.0 (Linux; U; Android 2.2; en-ca; SGH-T959D Build/FROYO) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
            .check(status.is(200))),

          12d -> exec(addCookie(Cookie("ckps_d", "d")))
            .exec(http("iPad 2")
            .get("${cbbcUrl}")
            .header("User-Agent", 
                      "Mozilla/5.0(iPad; U; CPU OS 4_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8F191 Safari/6533.18.5")
            .check(status.is(200))),

          12d -> exec(addCookie(Cookie("ckps_d", "d"))) 
            .exec(http("Samsung Galaxy Tab 2")
            .get("${cbbcUrl}")
            .header("User-Agent", 
                  "Mozilla/5.0 (Linux; U; Android 2.2; en-gb; GT-P1000 Build/FROYO) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
            .check(status.is(200))))

        setUp(scn.inject(
          rampUsersPerSec(10) to(300) during(30 minutes) 
        ).protocols(httpProtocol))
}
