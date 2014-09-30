package mediaselector

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
      .header("X-IP-Address", "be3d985a612d34e7cb08b7eda014bef6aa74dd4d8730d51f5044a6b6dc400;194.159.80.39")
    ) 

      setUp(scn.inject(
        rampUsersPerSec(10) to(900) during(20 minutes) 
      ).protocols(httpProtocol))
}
