package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder

/**
 * A simulation consists of:
 *  - protocol
 *  - defining requests
 *  - setting up the scenario
 *  - injecting users
 *
 * @see <a href="https://reqres.in/"> fake data generator </a>
 *
 */
class FindAllUsersSimulation extends Simulation {

  //protocol
  val URL = "https://reqres.in/"
  val httpProtocol: HttpProtocolBuilder = http.baseUrl(URL)
    .acceptHeader("application/json")
    .header("Content-Type", "application/json")


  //define requests
  def findUsers(): ChainBuilder =
    exec(http("find users").get("/api/users?page=2"))


  //setup scenario
  val scn: ScenarioBuilder = scenario("find users at second page")
    .exec(findUsers())

  //inject users
  setUp(
    scn.inject(atOnceUsers(100))
      .protocols(httpProtocol))

}
