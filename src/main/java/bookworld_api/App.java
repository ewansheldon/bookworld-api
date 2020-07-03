package bookworld_api;

import static spark.Spark.after;
import static spark.Spark.port;

import bookworld_api.repositories.BookRepository;
import bookworld_api.repositories.InMemoryBookRepository;
import bookworld_api.services.BookService;
import bookworld_api.services.CountryService;
import bookworld_api.web.BookController;
import bookworld_api.web.CountryController;

public class App {

  public static final int DEFAULT_PORT = 8080;

  public static void main(String[] strings) {
    port(getPort());

    after((request, response) -> {
      response.header("Access-Control-Allow-Origin", "*");
      response.header("Access-Control-Allow-Methods", "*");
    });

    BookRepository bookRepository = new InMemoryBookRepository();
    BookService bookService = new BookService(bookRepository);
    BookController bookController = new BookController(bookService);
    bookController.routes();

    CountryService countryService = new CountryService(bookService);
    CountryController countryController = new CountryController(countryService);
    countryController.routes();
  }

  private static int getPort() {
    String port = System.getenv("PORT");
    if (port != null) {
      return Integer.parseInt(port);
    }
    return DEFAULT_PORT;
  }
}
