package bookworld_api.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.request_objects.BookRequestObject;
import bookworld_api.services.BookService;
import bookworld_api.util.Server;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Spark;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

  private final Book BOOK = new Book(1, "Vile Bodies", "Evelyn Waugh", "GBR", "description",
      "thumnail");

  @Mock
  private BookService bookService;
  @Mock
  private TokenAuthenticator tokenAuthenticator;

  @BeforeEach
  void setUp() {
    Server.stop();
    new BookController(bookService, tokenAuthenticator);
  }

  @Test
  void saves_new_book_with_book_service() throws IOException, JSONException, SQLException {
    BookRequestObject request = new BookRequestObject("Vile Bodies", "Evelyn Waugh", "GBR");
    Book book = new Book(1, "Vile Bodies", "Evelyn Waugh", "GBR", "book description",
        "book-thumbnail");
    when(bookService.create(any(BookRequestObject.class))).thenReturn(book);
    given().port(Spark.port()).body(request).when().post("/books")
        .then().statusCode(201)
        .assertThat().body("id", equalTo((int) book.getId()))
        .assertThat().body("title", equalTo(book.getTitle()))
        .assertThat().body("author", equalTo(book.getAuthor()))
        .assertThat().body("country", equalTo(book.getCountry()))
        .assertThat().body("description", equalTo(book.getDescription()))
        .assertThat().body("thumbnail", equalTo(book.getThumbnail()));
  }

  @Test
  void gets_book_by_country() throws SQLException, CountryNotValidException {
    String country = "GBR";
    when(bookService.getBookFrom(country)).thenReturn(BOOK);
    given().port(Spark.port()).when().get("/books/" + country).then().statusCode(200)
        .assertThat().body("title", equalTo(BOOK.getTitle()))
        .assertThat().body("author", equalTo(BOOK.getAuthor()))
        .assertThat().body("country", equalTo(BOOK.getCountry()));
  }

  @Test
  void returns_error_status_and_message_when_invalid_country_error()
      throws CountryNotValidException, SQLException {
    String country = "xxx";
    when(bookService.getBookFrom(country)).thenThrow(new CountryNotValidException());
    given().port(Spark.port()).when().get("/books/" + country).then().statusCode(422).assertThat()
        .body(equalTo("Requested country is not valid"));
  }

  @Test
  void returns_all_books() throws SQLException {
    when(bookService.getAllBooks()).thenReturn(Collections.singletonList(BOOK));
    Book[] books = given().port(Spark.port()).when().get("/books").then().statusCode(200)
        .extract().as(Book[].class);

    assertEquals(1, books.length);
    Book book = books[0];
    assertEquals(BOOK.getTitle(), book.getTitle());
    assertEquals(BOOK.getAuthor(), book.getAuthor());
    assertEquals(BOOK.getCountry(), book.getCountry());
    assertEquals(BOOK.getDescription(), book.getDescription());
    assertEquals(BOOK.getThumbnail(), book.getThumbnail());
  }
}
