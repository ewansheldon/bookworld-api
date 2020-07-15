package bookworld_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.factories.BookFactory;
import bookworld_api.integrations.BookDataIntegration;
import bookworld_api.integrations.BookDataResponseObject;
import bookworld_api.repositories.BookRepository;
import bookworld_api.request_objects.BookRequestObject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

  public final Book BOOK = new Book("Vile Bodies", "Evelyn Waugh", "GBR", "Book description", "book-thumbnail");
  private BookService bookService;

  @Mock
  private BookRepository bookRepository;
  @Mock
  private BookDataIntegration bookDataIntegration;
  @Mock
  private BookFactory bookFactory;

  @BeforeEach
  void setUp() {
    bookService = new BookService(bookRepository, bookDataIntegration, bookFactory);
  }

  @Test
  void fetches_book_data_and_creates_a_book_with_book_repository()
      throws IOException, JSONException, SQLException {
    BookDataResponseObject bookDataResponse = new BookDataResponseObject(BOOK.getDescription(), BOOK.getThumbnail());
    BookRequestObject request = new BookRequestObject("Vile Bodies", "Evelyn Waugh", "GBR");
    when(bookDataIntegration.get(request)).thenReturn(bookDataResponse);
    when(bookFactory.create(request, bookDataResponse)).thenReturn(BOOK);
    when(bookRepository.create(BOOK)).thenReturn(BOOK);

    Book response = bookService.create(request);

    assertEquals(request.getTitle(), response.getTitle());
    assertEquals(request.getAuthor(), response.getAuthor());
    assertEquals(request.getCountry(), response.getCountry());
    assertEquals(bookDataResponse.description, response.getDescription());
    assertEquals(bookDataResponse.thumbnail, response.getThumbnail());
  }

  @Test
  void gets_countries_from_repository() throws SQLException {
    List<String> countries = Collections.singletonList("GBR");
    when(bookRepository.getCountries()).thenReturn(countries);
    List<String> response = bookService.getCountries();

    assertEquals(1, response.size());
    assertEquals(BOOK.getCountry(), response.get(0));
  }

  @Test
  void gets_book_from_country_from_repository() throws CountryNotValidException, SQLException {
    when(bookRepository.getByCountry(BOOK.getCountry())).thenReturn(BOOK);
    Book response = bookService.getBookFrom(BOOK.getCountry());

    assertEquals(BOOK.getTitle(), response.getTitle());
    assertEquals(BOOK.getAuthor(), response.getAuthor());
    assertEquals(BOOK.getCountry(), response.getCountry());
    assertEquals(BOOK.getDescription(), response.getDescription());
    assertEquals(BOOK.getThumbnail(), response.getThumbnail());
  }

  @Test
  void gets_all_books_from_repository() throws SQLException {
    when(bookRepository.getAll()).thenReturn(Collections.singletonList(BOOK));
    List<Book> response = bookService.getAllBooks();

    assertEquals(1, response.size());
    Book book = response.get(0);
    assertEquals(BOOK.getTitle(), book.getTitle());
    assertEquals(BOOK.getAuthor(), book.getAuthor());
    assertEquals(BOOK.getCountry(), book.getCountry());
    assertEquals(BOOK.getDescription(), book.getDescription());
    assertEquals(BOOK.getThumbnail(), book.getThumbnail());
  }
}
