package bookworld_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import bookworld_api.Book;
import bookworld_api.repositories.BookRepository;
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

  @BeforeEach
  void setUp() {
    bookService = new BookService(bookRepository);
  }

  @Test
  void creates_a_book_with_book_repository() {
    when(bookRepository.create(BOOK)).thenReturn(BOOK);

    Book response = bookService.create(BOOK);

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
}
