package bookworld_api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class APITest {

    @BeforeAll
    static void beforeAll() {
        App.main(new String[0]);
    }

    @Test
    void should_get_book_from_country_code_endpoint() {
        given().port(8080).when().request("GET", "/books/GBR")
                .then().statusCode(200)
                .assertThat().body("title", equalTo("Vile Bodies"))
                .assertThat().body("author", equalTo("Evelyn Waugh"))
                .assertThat().body("publication_date", equalTo("1930"));
    }

    @Test
    void should_get_countries_where_books_are_from() {
        given().port(8080).when().request("GET", "/countries")
                .then().statusCode(200)
                .assertThat().body("", hasItems("GBR", "PRT", "AUS"));
    }
}