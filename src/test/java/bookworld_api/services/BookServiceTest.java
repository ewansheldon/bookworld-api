package bookworld_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import bookworld_api.Book;
import bookworld_api.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

  private BookService bookService;

  @Mock
  private BookRepository bookRepository;

  @BeforeEach
  void setUp() {
    bookService = new BookService(bookRepository);
  }

  @Test
  void creates_a_book_with_book_repository() {
    Book book = new Book("Vile Bodies", "Evelyn Waugh", "1930", "GBR");
    when(bookRepository.create(book)).thenReturn(book);

    Book response = bookService.create(book);

    assertEquals(book.getTitle(), response.getTitle());
    assertEquals(book.getAuthor(), response.getAuthor());
    assertEquals(book.getPublicationDate(), response.getPublicationDate());
    assertEquals(book.getCountry(), response.getCountry());
  }
}
