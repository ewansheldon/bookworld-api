package bookworld_api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class APITest {

    @Test
    void should_get_book_from_country_code_endpoint() {
        App.main(new String[0]);
        given().port(80).when().request("GET", "/books/GBR")
                .then().statusCode(200)
                .assertThat().body("title", equalTo("Vile Bodies"))
                .assertThat().body("author", equalTo("Evelyn Waugh"))
                .assertThat().body("publication_date", equalTo("1930"));
    }
}