package bbc.mediaselector

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class MediaSelector extends Simulation {

  val httpProtocol = http
    .baseURL("http://open.stage.cwwtf.bbc.co.uk")

    val payload = csv("open-payload.csv").circular

    val scn = scenario("media-selector")
      .feed(payload)
      .exec(http("media-selector")
      .get("${selectURL}")
      .header("X-IP-Address", "8174fa5a37db4bfac904e4005604a4022f5ab1f26346eb5650458f402a340;194.159.80.39")
      .check(status.is(200))
    ) 

      setUp(scn.inject(
        rampUsersPerSec(10) to(750) during(20 minutes) 
      ).protocols(httpProtocol))
}
