package bookworld_api.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import bookworld_api.Book;
import bookworld_api.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Spark;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

  public static final int PORT = 8080;
  private BookController bookController;

  @Mock
  private BookService bookService;

  @BeforeEach
  void setUp() {
    Spark.port(PORT);
    bookController = new BookController(bookService);
    bookController.routes();
  }

  @Test
  void saves_new_book_with_country_service() {
    Book book = new Book("Vile Bodies", "Evelyn Waugh", "1930", "GBR");
    when(bookService.create(any(Book.class))).thenReturn(book);
    given().port(PORT).body(book).when().post("/books")
        .then().statusCode(201)
        .assertThat().body("title", equalTo(book.getTitle()))
        .assertThat().body("author", equalTo(book.getAuthor()))
        .assertThat().body("publicationDate", equalTo(book.getPublicationDate()));
  }
}
