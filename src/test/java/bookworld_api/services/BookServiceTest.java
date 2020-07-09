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
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

  public final Book BOOK = new Book("Vile Bodies", "Evelyn Waugh", "1930", "GBR");
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
  void fetches_book_data_and_creates_a_book_with_book_repository() {
    BookDataResponseObject bookDataResponse = new BookDataResponseObject("test description", "test thumbnail");
    BookRequestObject request = new BookRequestObject("Vile Bodies", "Evelyn Waugh", "GBR");
    when(bookDataIntegration.get(request)).thenReturn(bookDataResponse);
    when(bookFactory.create(request, bookDataResponse)).thenReturn(BOOK);
    when(bookRepository.create(BOOK)).thenReturn(BOOK);

    Book response = bookService.create(request);

    assertEquals(BOOK.getTitle(), response.getTitle());
    assertEquals(BOOK.getAuthor(), response.getAuthor());
    assertEquals(BOOK.getPublicationDate(), response.getPublicationDate());
    assertEquals(BOOK.getCountry(), response.getCountry());
  }

  @Test
  void gets_countries_from_repository() {
    List<String> countries = Collections.singletonList("GBR");
    when(bookRepository.getCountries()).thenReturn(countries);
    List<String> response = bookService.getCountries();

    assertEquals(1, response.size());
    assertEquals(BOOK.getCountry(), response.get(0));
  }

  @Test
  void gets_book_from_country_from_repository() throws CountryNotValidException {
    when(bookRepository.getBookByCountry(BOOK.getCountry())).thenReturn(BOOK);
    Book response = bookService.getBookFrom(BOOK.getCountry());

    assertEquals(BOOK.getTitle(), response.getTitle());
    assertEquals(BOOK.getAuthor(), response.getAuthor());
    assertEquals(BOOK.getPublicationDate(), response.getPublicationDate());
    assertEquals(BOOK.getCountry(), response.getCountry());
  }
}
