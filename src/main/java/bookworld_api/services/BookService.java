package bookworld_api.services;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.factories.BookFactory;
import bookworld_api.integrations.BookDataIntegration;
import bookworld_api.integrations.BookDataResponseObject;
import bookworld_api.repositories.BookRepository;
import bookworld_api.request_objects.BookRequestObject;
import java.util.List;

public class BookService {

  private BookRepository bookRepository;
  private BookDataIntegration bookDataIntegration;
  private BookFactory bookFactory;

  public BookService(BookRepository bookRepository, BookDataIntegration bookDataIntegration, BookFactory bookFactory) {
    this.bookRepository = bookRepository;
    this.bookDataIntegration = bookDataIntegration;
    this.bookFactory = bookFactory;
  }

  public Book create(BookRequestObject request) {
    BookDataResponseObject bookDataResponse = bookDataIntegration.get(request);
    Book book = bookFactory.create(request, bookDataResponse);
    return bookRepository.create(book);
  }

  public List<String> getCountries() {
    return bookRepository.getCountries();
  }

  public Book getBookFrom(String country_code) throws CountryNotValidException {
    return bookRepository.getBookByCountry(country_code);
  }
}
