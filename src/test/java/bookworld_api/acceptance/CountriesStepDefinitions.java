package bookworld_api.acceptance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

import bookworld_api.Book;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class CountriesStepDefinitions {

  private Response res;

  @Given("there is a book from {string}")
  public void thereIsABookFrom(String country) {
    Book book = new Book("Vile Bodies", "Evelyn Waugh", country);
    given().port(8080).body(book).when().post("POST", "/books").then().statusCode(201);
  }

  @When("the user fetches all countries")
  public void theUserFetchesAllCountries() {
    res = given().port(8080).when().get("/countries");
  }

  @Then("the user receives {string} in the list of countries")
  public void theUserReceivesInTheListOfCountries(String country) {
    res.then().statusCode(200).assertThat().body("", hasItems("GBR", "PRT", "AUS"));
  }
}
