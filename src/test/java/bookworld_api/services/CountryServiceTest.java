package bookworld_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
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
  void returns_all_countries_from_book_service() {
    String gbr = "GBR";
    List<String> countries = Collections.singletonList(gbr);
    when(bookService.getCountries()).thenReturn(countries);

    List<String> response = countryService.getAll();
    assertEquals(response.size(), 1);
    assertEquals(response.get(0), gbr);
  }
}
