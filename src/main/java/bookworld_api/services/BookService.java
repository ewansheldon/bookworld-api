package bookworld_api.services;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.factories.BookFactory;
import bookworld_api.integrations.BookDataIntegration;
import bookworld_api.integrations.BookDataResponseObject;
import bookworld_api.repositories.BookRepository;
import bookworld_api.request_objects.BookRequestObject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.json.JSONException;

public class BookService {

  private BookRepository bookRepository;
  private BookDataIntegration bookDataIntegration;
  private BookFactory bookFactory;

  public BookService(BookRepository bookRepository, BookDataIntegration bookDataIntegration, BookFactory bookFactory) {
    this.bookRepository = bookRepository;
    this.bookDataIntegration = bookDataIntegration;
    this.bookFactory = bookFactory;
  }

  public Book create(BookRequestObject request) throws IOException, JSONException, SQLException {
    BookDataResponseObject bookDataResponse = bookDataIntegration.get(request);
    Book book = bookFactory.create(request, bookDataResponse);
    return bookRepository.create(book);
  }

  public List<String> getCountries() throws SQLException {
    return bookRepository.getCountries();
  }

  public Book getBookFrom(String country_code) throws CountryNotValidException, SQLException {
    return bookRepository.getByCountry(country_code);
  }

  public List<Book> getAllBooks() throws SQLException {
    return bookRepository.getAll();
  }
}
