package bbc.radioplayer

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RadioPlayer extends Simulation {

    val httpProtocol = http
      .baseURL("http://www.stage.bbc.co.uk/radio/player/")
      .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
      .acceptHeader("""text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""")
      .acceptEncodingHeader("""gzip, deflate""")
      .acceptLanguageHeader("""en-gb,en;q=0.5""")
      .connection("""keep-alive""")
      .userAgentHeader("""Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0""")

    val liveStreamData = csv("live-stream.csv").circular
    val flagpoleHeaders = Map(
      "flagpole-radio-playlister" -> "ON", "flagpole-bbcme-status" -> "GREEN", 
      "flagpole-bbcme-favouriteplugin" -> "ON", "flagpole-bbcme-memodule" -> "ON", 
      "flagpole-programmes-apsgateway" -> "ON")

    val scn = scenario("Radio Player")
      .feed(liveStreamData)
      .randomSwitch(
        17d -> exec(http("Live Stream").get("${stationId}")
          .headers(flagpoleHeaders)
          .check(status.is(200))),

        17d -> exec(http("On Demand Stream").get("b04gk6kv") // episodePID
          .headers(flagpoleHeaders)
          .check(status.is(200))),

        17d -> exec(http("Clip Stream").get("p01z4z5y") // clipPID
          .headers(flagpoleHeaders)
          .check(status.is(200))),

        11d -> exec(http("Pop-up Station Stream").get("bbc_radio_two_eurovision") 
          .headers(flagpoleHeaders)
          .check(status.is(200))),

        10d -> exec(http("Track Image Feed").get("trackimage/nzzgbn")  // recordID
          .headers(flagpoleHeaders)
          .check(status.is(200))),

        7d -> exec(http("More Episodes Feed").get("b04gk6kv/episodes/1") // episodePID & page
          .headers(flagpoleHeaders)  
          .check(status.is(200))),

        7d -> exec(http("More Like This Feed").get("b04gk6kv/morelikethis/1") // episodePID & page
          .headers(flagpoleHeaders)  
          .check(status.is(200))),

        5d -> exec(http("Live Now/Next Feed").get("${stationId}/nownext") 
          .headers(flagpoleHeaders)  
          .check(status.is(200))),

        3d -> exec(http("Live Schedule Feed").get("${stationId}/schedule") 
          .headers(flagpoleHeaders)  
          .check(status.is(200))),

        3d -> exec(http("Favourites Feed").get("b04gk6kv/favourites") 
          .headers(flagpoleHeaders)  
          .check(status.is(200))),

        3d -> exec(http("Stations Feed").get("stations") 
          .headers(flagpoleHeaders)  
          .check(status.is(200))))

      setUp(scn.inject(
        rampUsersPerSec(10) to(200) during(3 minutes),
        constantUsersPerSec(200) during(17 minutes)
        ).protocols(httpProtocol))
}
