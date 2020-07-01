package bookworld_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import bookworld_api.repositories.CountryRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

  @Mock
  private CountryRepository countryRepository;

  @Test
  void returns_all_countries_from_repository() {
    CountryService countryService = new CountryService(countryRepository);
    String gbr = "GBR";
    List<String> countries = Collections.singletonList(gbr);
    when(countryRepository.getAll()).thenReturn(countries);

    List<String> response = countryService.getAll();
    assertEquals(response.size(), 1);
    assertEquals(response.get(0), gbr);
  }
}
