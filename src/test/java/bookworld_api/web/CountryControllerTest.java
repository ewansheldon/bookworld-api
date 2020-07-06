package bookworld_api.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.when;

import bookworld_api.services.CountryService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Spark;

@ExtendWith(MockitoExtension.class)
public class CountryControllerTest {

  private CountryController countryController;

  @Mock
  CountryService countryService;

  @BeforeEach
  void setUp() {
    countryController = new CountryController(countryService);
    countryController.routes();
  }

  @Test
  void gets_all_countries_from_country_service() {
    List<String> countries = Collections.singletonList("GBR");
    when(countryService.getAll()).thenReturn(countries);

    given().port(Spark.port()).when().get("/countries")
        .then().statusCode(200)
        .assertThat().body("", hasItems("GBR"));
  }
}
