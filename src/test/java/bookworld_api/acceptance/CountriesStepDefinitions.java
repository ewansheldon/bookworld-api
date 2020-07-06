package bookworld_api.acceptance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import spark.Spark;

public class CountriesStepDefinitions {

  private Response res;

  @When("the user fetches all countries")
  public void theUserFetchesAllCountries() {
    res = given().port(Spark.port()).when().get("/countries");
  }

  @Then("the user receives {string} in the list of countries")
  public void theUserReceivesInTheListOfCountries(String country) {
    res.then().statusCode(200).assertThat().body("", hasItems(country));
  }
}
