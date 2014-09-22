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
      .header("X-IP-Address", "cd44794333675ecb66c49cf1302d223c89f87b9f9860703d503a603fe3880;194.159.80.39")
    ) 

      setUp(scn.inject(
        rampUsers(500) over(10.minutes)
      ).protocols(httpProtocol))
}
