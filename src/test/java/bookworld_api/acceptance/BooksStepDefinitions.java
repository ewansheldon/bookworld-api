package bookworld_api.acceptance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import spark.Spark;

public class BooksStepDefinitions {

  private Response res;

  @When("the user requests a book from {string}")
  public void theUserRequestsABookFrom(String country) {
    res = given().port(Spark.port()).when().get("/books/" + country);
  }

  @Then("the user receives the book")
  public void theUserReceivesTheBook() {
    res.then().statusCode(200).assertThat().body("title", equalTo("Vile Bodies"))
        .assertThat().body("author", equalTo("Evelyn Waugh"))
        .assertThat().body("publicationDate", equalTo("1930"))
        .assertThat().body("country", equalTo("GBR"));
  }
}
