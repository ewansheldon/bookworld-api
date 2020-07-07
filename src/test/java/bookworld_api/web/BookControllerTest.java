package bookworld_api.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import bookworld_api.entities.Book;
import bookworld_api.services.BookService;
import bookworld_api.util.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

  @Mock
  private BookService bookService;

  @BeforeEach
  void setUp() {
    Server.stop();
    new BookController(bookService);
  }

  @Test
  void saves_new_book_with_country_service() {
    Book book = new Book("Vile Bodies", "Evelyn Waugh", "1930", "GBR");
    when(bookService.create(any(Book.class))).thenReturn(book);
    given().port(4567).body(book).when().post("/books")
        .then().statusCode(201)
        .assertThat().body("title", equalTo(book.getTitle()))
        .assertThat().body("author", equalTo(book.getAuthor()))
        .assertThat().body("publicationDate", equalTo(book.getPublicationDate()));
  }
}
