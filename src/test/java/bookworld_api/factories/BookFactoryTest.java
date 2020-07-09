package bookworld_api.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bookworld_api.entities.Book;
import bookworld_api.integrations.BookDataResponseObject;
import bookworld_api.request_objects.BookRequestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookFactoryTest {

  private BookFactory bookFactory;

  @BeforeEach
  void setUp() {
    bookFactory = new BookFactory();
  }

  @Test
  void creates_a_book_using_request_and_book_data() {
    BookRequestObject request = new BookRequestObject("Vile Bodies", "Evelyn Waugh", "GBR");
    BookDataResponseObject dataResponse = new BookDataResponseObject("Book description", "book-thumbnail");

    Book book = bookFactory.create(request, dataResponse);

    assertEquals(request.getTitle(), book.getTitle());
    assertEquals(request.getAuthor(), book.getAuthor());
    assertEquals(request.getCountry(), book.getCountry());
    assertEquals(dataResponse.description, book.getDescription());
    assertEquals(dataResponse.thumbnail, book.getThumbnail());
  }
}
