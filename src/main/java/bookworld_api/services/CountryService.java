package bookworld_api.services;

import java.util.List;

public class CountryService {

  private BookService bookService;

  public CountryService(BookService bookService) {
    this.bookService = bookService;
  }

  public List<String> getAll() {
    return bookService.getCountries();
  }
}
