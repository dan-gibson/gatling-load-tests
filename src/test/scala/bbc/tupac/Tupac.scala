package bbc.tupac

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Tupac extends Simulation {

    val httpProtocol = http
      .baseURL("https://api.stage.bbc.co.uk/music/artists/")
      .connection("keep-alive")

    val artistData = csv("artists.csv").circular

    val scn = scenario("Artist Id")
      .feed(artistData)
      .exec(http("Artist Id")
      .get("${artistId}")
      .header("Cache-Control", "no-cache")
      .check(status.is(200)))

      setUp(scn.inject(
        constantUsersPerSec(4) during(20 minutes)
      ).protocols(httpProtocol))
}
