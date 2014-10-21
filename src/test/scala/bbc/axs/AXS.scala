package bbc.axs

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AXS extends Simulation {

  val httpProtocol = http
    .baseURL("http://open.stage.bbc.co.uk/axs/open/authxml")

    val mediaSet = csv("axs/media-set.csv").circular
    val versionPid = csv("axs/version-pid").circular

    val scn = scenario("AXS")
      .feed(mediaSet)
      .feed(versionPid)
      .exec(http("AXS")
      .get("?media_set=${mediaSet}&version_pid=${versionPid}&format=base64")
      .check(status.is(200))
    ) 

      setUp(scn.inject(
        rampUsersPerSec(10) to(100) during(3 minutes) randomized,
        constantUsersPerSec(100) during(7 minutes) randomized
      ).protocols(httpProtocol))
}
