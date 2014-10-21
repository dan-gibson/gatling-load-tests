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
    val locationId = csv("homepage/location-id.csv").circular
    val newsRegionId = csv("homepage/news-region-id.csv").circular

    val scn = scenario("Homepage")
        .feed(locCookie)
        .feed(locationId)
        .feed(newsRegionId)
        .exec(addCookie(Cookie("locserv", "${locCookie}")))

        .exec(http("Domestic").get("""/""").check(status.is(200))
        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")
        .resources(
            http("domestic-weather").get("""/home/four/modules/h4weather/domestic/${locationId}/Trafford%2BPark/en-GB"""),
            http("domestic-json").get("""/home/four/modules/h4discoveryzone/1/domestic/default/default/${newsRegionId}/en-GB.json"""))) 

        .exec(http("Wales").get("""/wales/""").check(status.is(200))
        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")
        .resources(
            http("wales-weather").get("""/home/four/modules/h4weather/wales/${locationId}/Trafford%2BPark/en-GB"""),
            http("wales-json").get("""/home/four/modules/h4discoveryzone/1/wales/default/default/${newsRegionId}/en-GB.json"""))) 


        .exec(http("Scotland").get("""/scotland/""").check(status.is(200))
        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")
        .resources(
            http("scotland-weather").get("""/home/four/modules/h4weather/scotland/${locationId}/Trafford%2BPark/en-GB"""),
            http("scotland-json").get("""/home/four/modules/h4discoveryzone/1/scotland/default/default/${newsRegionId}/en-GB.json"""))) 

        .exec(http("northernireland").get("""/northernireland/""").check(status.is(200))
        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")
        .resources(
            http("ni-weather").get("""/home/four/modules/h4weather/northernireland/${locationId}/Trafford%2BPark/en-GB"""),
            http("ni-json").get("""/home/four/modules/h4discoveryzone/1/northernireland/default/default/${newsRegionId}/en-GB.json"""))) 

        .exec(http("cymru").get("""/cymru/""").check(status.is(200))
        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")
        .resources(
            http("cymru-weather").get("""/home/four/modules/h4weather/cymru/${locationId}/Trafford%2BPark/en-GB"""),
            http("cymru-json").get("""/home/four/modules/h4discoveryzone/1/cymru/default/default/${newsRegionId}/en-GB.json"""))) 

        .exec(http("alba").get("""/alba/""").check(status.is(200))
        .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0")
        .resources(
            http("alba-weather").get("""/home/four/modules/h4weather/alba/${locationId}/Trafford%2BPark/en-GB"""),
            http("alba-json").get("""/home/four/modules/h4discoveryzone/1/alba/default/default/${newsRegionId}/en-GB.json"""))) 
      
      setUp(
        scn.inject(constantUsersPerSec(10) during(10 seconds)
      ).protocols(httpProtocol))
}




