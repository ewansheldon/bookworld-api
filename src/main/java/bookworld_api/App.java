package bookworld_api;

import static spark.Spark.after;

import bookworld_api.factories.BookFactory;
import bookworld_api.integrations.BookDataIntegration;
import bookworld_api.repositories.BookRepository;
import bookworld_api.repositories.InMemoryBookRepository;
import bookworld_api.request_objects.BookRequestObject;
import bookworld_api.services.BookService;
import bookworld_api.services.CountryService;
import bookworld_api.web.BookController;
import bookworld_api.web.CountryController;
import spark.Spark;

public class App {

  public static final int DEFAULT_PORT = 8080;

  public static void main(String[] strings) {
    Spark.port(getPort());

    after((request, response) -> {
      response.header("Access-Control-Allow-Origin", "*");
      response.header("Access-Control-Allow-Methods", "*");
    });

    BookRepository bookRepository = new InMemoryBookRepository();
    BookDataIntegration bookDataIntegration = new BookDataIntegration();
    BookFactory bookFactory = new BookFactory();
    BookService bookService = new BookService(bookRepository, bookDataIntegration, bookFactory);
    new BookController(bookService);

    CountryService countryService = new CountryService(bookService);
    new CountryController(countryService);

    bookService.create(new BookRequestObject("Vile Bodies", "Evelyn Waugh", "GBR"));
  }

  public static int getPort() {
    String port = System.getenv("PORT");
    if (port != null) {
      return Integer.parseInt(port);
    }
    return DEFAULT_PORT;
  }
}
