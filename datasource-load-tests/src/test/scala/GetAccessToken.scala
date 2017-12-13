import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class GetAccessToken() {

  def getAccessToken(): ChainBuilder = {
    getAccessToken("auth.json")
  }

  def getAccessToken(authFile:String): ChainBuilder = {
    exec(http("Get Access Token")
      .post(LoadConfiguration.AUTH_URL)
      .header("Content-Type", "application/json")
      .header("Authorization", LoadConfiguration.API_KEY)
      .body(ELFileBody(authFile)).asJSON
      .check(status.is(201))
      .check(jsonPath("$.accessToken").saveAs("accessToken"))
    )
  }

}
