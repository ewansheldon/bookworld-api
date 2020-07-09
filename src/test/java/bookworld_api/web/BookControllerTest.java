package bookworld_api.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.request_objects.BookRequestObject;
import bookworld_api.services.BookService;
import bookworld_api.util.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Spark;

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
    BookRequestObject request = new BookRequestObject("Vile Bodies", "Evelyn Waugh", "GBR");
    Book book = new Book("Vile Bodies", "Evelyn Waugh", "1930", "GBR");
    when(bookService.create(any(BookRequestObject.class))).thenReturn(book);
    given().port(Spark.port()).body(request).when().post("/books")
        .then().statusCode(201)
        .assertThat().body("title", equalTo(book.getTitle()))
        .assertThat().body("author", equalTo(book.getAuthor()))
        .assertThat().body("publicationDate", equalTo(book.getPublicationDate()));
  }

  @Test
  void returns_error_status_and_message_when_invalid_country_error() throws CountryNotValidException {
    String country = "xxx";
    when(bookService.getBookFrom(country)).thenThrow(new CountryNotValidException());
    given().port(Spark.port()).when().get("/books/" + country).then().statusCode(422).assertThat()
        .body(equalTo("Requested country is not valid"));
  }
}
