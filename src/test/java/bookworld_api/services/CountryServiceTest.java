package bookworld_api.services;

import static bookworld_api.web.JsonHandler.getJsonFrom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

  private CountryService countryService;

  @Mock
  private BookService bookService;

  @BeforeEach
  void setUp() {
    countryService = new CountryService(bookService);
  }

  @Test
  void returns_all_countries_from_book_service() throws SQLException {
    String gbr = "GBR";
    List<String> countries = Collections.singletonList(gbr);
    when(bookService.getCountries()).thenReturn(countries);

    List<String> response = countryService.getAll();
    assertEquals(response.size(), 1);
    assertEquals(response.get(0), gbr);
  }

  @Test
  void dummy_good_reads_requests() throws IOException, JSONException {
    JSONObject object = getJsonFrom("https://www.googleapis.com/books/v1/volumes?q=vile+bodies+evelyn+waugh");
    System.out.println(object.getJSONArray("items").getJSONObject(0).toString());
  }
}
