package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder

/**
 *
 * A simulation consists of:
 *  - protocol
 *  - defining requests
 *  - setting up the scenario
 *  - injecting users
 *
 * @see <a href="https://reqres.in/"> fake data generator </a>
 */
class CreateUserSimulation extends Simulation {

  //language=JSON
  val CREATE_USER_JSON_BODY: String = """{"name":"Bob","job":"painter"}""".stripMargin

  //protocol
  val URL = "https://reqres.in/"
  val httpProtocol: HttpProtocolBuilder = http.baseUrl(URL)
    .acceptHeader("application/json")
    .header("Content-Type", "application/json")


  //define requests
  def createUser(): ChainBuilder = {
    exec(
      http("create a user").post("/api/users")
        .body(StringBody(CREATE_USER_JSON_BODY)).asJson)

  }


  //setup scenario
  val scn: ScenarioBuilder = scenario("create few users")
    .exec(createUser())

  //inject users
  setUp(
    scn.inject(atOnceUsers(100))
      .protocols(httpProtocol))

}
