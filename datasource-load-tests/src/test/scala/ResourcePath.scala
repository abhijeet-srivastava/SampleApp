/**
  * Created by oury on 09/10/2016.
  */

class ResourcePath(dumpId: String) {
  val ACCESS_TOKEN = "https://api-staging.cvent.com/auth/v1/access_token"
  val PURGE_DATA = "/reporting/v1/report/dataDump/" + dumpId + "/purge"
  val DATA_STATUS = "/reporting/v1/report/dataDump/" + dumpId + "/status"
}