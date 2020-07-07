package bookworld_api.services;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.repositories.BookRepository;
import java.util.List;

public class BookService {

  private BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Book create(Book book) {
    return bookRepository.create(book);
  }

  public List<String> getCountries() {
    return bookRepository.getCountries();
  }

  public Book getBookFrom(String country_code) throws CountryNotValidException {
    return bookRepository.getBookByCountry(country_code);
  }
}
