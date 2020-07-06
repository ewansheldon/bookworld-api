package bookworld_api.acceptance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

import bookworld_api.entities.Book;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import spark.Spark;

public class CountriesStepDefinitions {

  private Response res;

  @Given("there is a book from {string}")
  public void thereIsABookFrom(String country) {
    Book book = new Book("Vile Bodies", "Evelyn Waugh", "1930", country);
    given().port(Spark.port()).body(book).when().post("/books").then().statusCode(201);
  }

  @When("the user fetches all countries")
  public void theUserFetchesAllCountries() {
    res = given().port(Spark.port()).when().get("/countries");
  }

  @Then("the user receives {string} in the list of countries")
  public void theUserReceivesInTheListOfCountries(String country) {
    res.then().statusCode(200).assertThat().body("", hasItems(country));
  }
}
