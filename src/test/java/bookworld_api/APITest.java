package bookworld_api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class APITest {

  @BeforeAll
  static void beforeAll() {
    App.main(new String[0]);
  }

  @Test
  void should_get_book_from_country_code_endpoint() {
    given().port(8080).when().get("/books/GBR")
        .then().statusCode(200)
        .assertThat().body("title", equalTo("Vile Bodies"))
        .assertThat().body("author", equalTo("Evelyn Waugh"))
        .assertThat().body("publicationDate", equalTo("1930"));
  }

  @Test
  void should_get_countries_where_books_are_from() {
    given().port(8080).when().get("/countries")
        .then().statusCode(200)
        .assertThat().body("", hasItems("GBR", "PRT", "AUS"));
  }
}