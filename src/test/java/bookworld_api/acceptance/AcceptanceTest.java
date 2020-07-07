package bookworld_api.acceptance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import bookworld_api.entities.Book;
import bookworld_api.repositories.BookRepository;
import bookworld_api.repositories.InMemoryBookRepository;
import bookworld_api.services.BookService;
import bookworld_api.services.CountryService;
import bookworld_api.util.Server;
import bookworld_api.web.BookController;
import bookworld_api.web.CountryController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;

public class AcceptanceTest {

  public static final Book BOOK = new Book("Vile Bodies", "Evelyn Waugh", "1930", "GBR");

  @BeforeAll
  static void beforeAll() {
    Server.stop();

    BookRepository bookRepository = new InMemoryBookRepository();
    BookService bookService = new BookService(bookRepository);
    new BookController(bookService);

    CountryService countryService = new CountryService(bookService);
    new CountryController(countryService);
  }

  @Test
  void fetches_a_book_from_requested_country() {
    given().port(Spark.port()).body(BOOK).when().post("/books");

    given().port(Spark.port()).when().get("/books/" + BOOK.getCountry()).then().statusCode(200).assertThat()
        .body("title", equalTo("Vile Bodies"))
        .assertThat().body("author", equalTo("Evelyn Waugh"))
        .assertThat().body("publicationDate", equalTo("1930"))
        .assertThat().body("country", equalTo("GBR"));
  }



  @Test
  void fetches_all_countries_that_books_are_tagged_in() {
    given().port(Spark.port()).body(BOOK).when().post("/books");

    given().port(Spark.port()).when().get("/countries").then().statusCode(200).assertThat().body("", hasItems(BOOK.getCountry()));
  }
}
