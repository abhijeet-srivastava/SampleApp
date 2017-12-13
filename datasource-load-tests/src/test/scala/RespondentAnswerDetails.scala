import java.util.UUID._

import com.cvent.gatlingcommon._
import io.gatling.core.Predef._
import io.gatling.core.body.ElFileBody
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

class RespondentAnswerDetails extends Simulation {

  val myUsers =   java.lang.Integer.getInteger("users", 25)
  val myRamp  =   java.lang.Integer.getInteger("ramp", 300)
  val url  = "http://discovery-staging.core.cvent.org/reporting-service-L2";
  val debug =  java.lang.Integer.getInteger("debug", 0);
  val environmentName = "L2";
  
  val getAuth = new GetAccessToken()
  //val dumpId = randomUUID.toString
  //val resourcePathObj = new ResourcePath(dumpId);
  val httpConf = http
  .headers(DefaultHeaders.baseHeader)
  .baseURL(url)
  .disableCaching

  val httpConfDebug = http
  .headers(DefaultHeaders.baseHeader)
  .baseURL(url)
  .disableCaching
  .extraInfoExtractor(extraInfo => List(extraInfo.request, extraInfo.response.body))

  val scn = scenario("Run Performance Report").group("runReport") {
    //make an authentication call to pull down token and save as accessToken
    exec(getAuth.getAccessToken)
    .pause(3)

    .repeat(1) {
      val dumpId = randomUUID.toString
      val resourcePathObj = new ResourcePath(dumpId);
      exec(http("requestDataAsync")
      .post(url)
      .queryParam("dataDumpId", "dump_" + dumpId)
      //.queryParam("dumpServiceUrl", "http://discovery-alpha.core.cvent.org/reporting-dump-client-service-alpha")
      .queryParam("dumpServiceUrl", "http://localhost:8088")
      .queryParam("environment", environmentName)
      .header("Authorization", "bearer ${accessToken}")
      .body(ElFileBody("respondentAnswerdetails.json")).asJSON
      .check(status.is(200))
      .check(jsonPath("$.status").saveAs("dataStatus")))
        .asLongAs(session =>  session("dataStatus").as[String] == "STARTED") {
        exec(http("Data Status call")
        .get(resourcePathObj.DATA_STATUS)
        .header("Authorization", "bearer ${accessToken}")
        .check(status.is(200))
        .check(jsonPath("$.status").saveAs("dataStatus")))
        .pause(LoadConfiguration.STATUS_CHECK_INTERVAL)
      
      }
        .asLongAs(session => session("dataStatus").as[String] == "CREATED") {
        exec(http("Data Status call")
        .get(resourcePathObj.DATA_STATUS)
        .header("Authorization", "bearer ${accessToken}")
        .check(status.is(200))
        .check(jsonPath("$.status").saveAs("dataStatus")))
        .pause(LoadConfiguration.STATUS_CHECK_INTERVAL)
      }
    }
  }
  if (debug == 1) {
    setUp(scn.inject(atOnceUsers(myUsers))).protocols(httpConfDebug)
    //setUp(scn.inject(rampUsers(30) over (60 seconds)).protocols(httpConfDebug))
  } else {
    //setUp(scn.inject(atOnceUsers(myUsers))).protocols(httpConfDebug)
    setUp(scn.inject(rampUsers(myUsers) over (myRamp seconds)).protocols(httpConf))
  }
}