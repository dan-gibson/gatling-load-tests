package bbc.searchsuggest

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class SearchSuggest extends Simulation {

    val httpProtocol = http
      .baseURL("http://open.stage.cwwtf.bbc.co.uk")
      .connection("keep-alive")

    val uniqueSuggestionsData = csv("unique_suggestions.csv").circular

    val scn = scenario("Search Suggest")
      .feed(uniqueSuggestionsData)
      .exec(http("Search Suggest Query")
      .get("/search-suggest/suggest?q=${uniqueSuggestions}&mediatype=video&scope=iplayer&format=blq-1")
      .check(status.is(200)))

      setUp(scn.inject(
        rampUsersPerSec(10) to(200) during(3 minutes),
        constantUsersPerSec(200) during(17 minutes)
      ).protocols(httpProtocol))
}
