package bookworld_api.acceptance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import bookworld_api.entities.Book;
import bookworld_api.factories.BookFactory;
import bookworld_api.integrations.BookDataIntegration;
import bookworld_api.repositories.BookRepository;
import bookworld_api.repositories.InMemoryBookRepository;
import bookworld_api.request_objects.BookRequestObject;
import bookworld_api.request_objects.UpdateBookRequestObject;
import bookworld_api.services.BookService;
import bookworld_api.services.CountryService;
import bookworld_api.util.Server;
import bookworld_api.web.BookController;
import bookworld_api.web.CountryController;
import bookworld_api.web.GoogleTokenAuthenticator;
import bookworld_api.web.TokenAuthenticator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;

public class AcceptanceTest {

  public static final BookRequestObject BOOK_REQUEST = new BookRequestObject("Vile Bodies",
      "Evelyn Waugh", "GBR");

  @BeforeAll
  static void beforeAll() {
    Server.stop();

    BookRepository bookRepository = new InMemoryBookRepository();
    BookDataIntegration bookDataIntegration = new BookDataIntegration();
    BookFactory bookFactory = new BookFactory();
    BookService bookService = new BookService(bookRepository, bookDataIntegration, bookFactory);
    TokenAuthenticator tokenAuthenticator = mock(TokenAuthenticator.class);
    new BookController(bookService, tokenAuthenticator);

    CountryService countryService = new CountryService(bookService);
    new CountryController(countryService);
  }

  @Test
  void fetches_a_book_from_requested_country() {
    given().port(Spark.port()).body(BOOK_REQUEST).when().post("/books");

    given().port(Spark.port()).when().get("/books/" + BOOK_REQUEST.getCountry()).then()
        .statusCode(200)
        .assertThat().body("title", equalTo("Vile Bodies"))
        .assertThat().body("author", equalTo("Evelyn Waugh"))
        .assertThat().body("country", equalTo("GBR"))
        .assertThat().body("description", notNullValue())
        .assertThat().body("thumbnail", notNullValue());
  }

  @Test
  void returns_error_when_requested_country_is_not_valid() {
    given().port(Spark.port()).when().get("/books/xxx").then().statusCode(422).assertThat()
        .body(equalTo("Requested country is not valid"));
  }

  @Test
  void fetches_all_countries_that_books_are_tagged_in() {
    given().port(Spark.port()).body(BOOK_REQUEST).when().post("/books");

    given().port(Spark.port()).when().get("/countries").then().statusCode(200).assertThat()
        .body("", hasItems(BOOK_REQUEST.getCountry()));
  }

  @Test
  void fetches_all_books() {
    given().port(Spark.port()).body(BOOK_REQUEST).when().post("/books");

    Book[] books = given().port(Spark.port()).when().get("/books").then().statusCode(200)
        .extract().as(Book[].class);

    assertEquals(1, books.length);
    Book book = books[0];
    assertEquals(BOOK_REQUEST.getTitle(), book.getTitle());
    assertEquals(BOOK_REQUEST.getAuthor(), book.getAuthor());
    assertEquals(BOOK_REQUEST.getCountry(), book.getCountry());
    assertNotNull(book.getDescription());
    assertNotNull(book.getThumbnail());
  }

  @Test
  void creates_a_book() {
    Book book = given().port(Spark.port()).body(BOOK_REQUEST).when().post("/books").then()
        .statusCode(201).extract().as(Book.class);

    assertEquals(BOOK_REQUEST.getTitle(), book.getTitle());
    assertEquals(BOOK_REQUEST.getAuthor(), book.getAuthor());
    assertEquals(BOOK_REQUEST.getCountry(), book.getCountry());
    assertNotNull(book.getDescription());
    assertNotNull(book.getThumbnail());
  }

  @Test
  void updates_a_book() {
    Book book = given().port(Spark.port()).body(BOOK_REQUEST).when().post("/books").then().extract()
        .as(Book.class);

    UpdateBookRequestObject updateRequest = new UpdateBookRequestObject(book.getId(), "new title",
        "new author", "ESP", "new description", "new thumbnail");
    Book updatedBook = given().port(Spark.port()).body(updateRequest).when()
        .patch("/books/" + book.getId())
        .then().statusCode(200).extract().as(Book.class);

    assertEquals(book.getId(), updatedBook.getId());
    assertEquals(updateRequest.getTitle(), updatedBook.getTitle());
    assertEquals(updateRequest.getAuthor(), updatedBook.getAuthor());
    assertEquals(updateRequest.getCountry(), updatedBook.getCountry());
    assertEquals(updateRequest.getDescription(), updatedBook.getDescription());
    assertEquals(updateRequest.getThumbnail(), updatedBook.getThumbnail());
  }
}
