package bookworld_api.repositories;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCountryRepository implements CountryRepository {

  private List<String> countries;

  public InMemoryCountryRepository() {
    this.countries = new ArrayList<>();
  }

  public List<String> getAll() {
    return countries;
  }
}
