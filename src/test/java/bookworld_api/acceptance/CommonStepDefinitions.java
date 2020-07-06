package bookworld_api.acceptance;

import static io.restassured.RestAssured.given;

import bookworld_api.entities.Book;
import io.cucumber.java.en.Given;
import spark.Spark;

public class CommonStepDefinitions {

  public Book book;

  @Given("there is a book from {string}")
  public void thereIsABookFrom(String country) {
    book = new Book("Vile Bodies", "Evelyn Waugh", "1930", country);
    given().port(Spark.port()).body(book).when().post("/books").then().statusCode(201);
  }
}
