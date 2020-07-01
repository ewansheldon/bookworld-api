package bookworld_api.services;

import bookworld_api.repositories.CountryRepository;
import java.util.List;

public class CountryService {

  private CountryRepository countryRepository;

  public CountryService(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }

  public List<String> getAll() {
    return countryRepository.getAll();
  }
}
