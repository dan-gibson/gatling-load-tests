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
      .check(status.is(200))
    ) 

      setUp(scn.inject(
        rampUsersPerSec(10) to(750) during(20 minutes) 
      ).protocols(httpProtocol))
}
