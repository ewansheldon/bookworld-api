package bookworld_api;

import bookworld_api.factories.BookFactory;
import bookworld_api.integrations.BookDataIntegration;
import bookworld_api.repositories.BookRepository;
import bookworld_api.repositories.PostgresBookRepository;
import bookworld_api.services.BookService;
import bookworld_api.services.CountryService;
import bookworld_api.web.BookController;
import bookworld_api.web.CorsConfig;
import bookworld_api.web.CountryController;
import spark.Spark;

public class BookWorld {

  public static final int DEFAULT_PORT = 8080;

  public static void main(String[] strings) {
    Spark.port(getPort());

    CorsConfig.configure();

    BookRepository bookRepository = new PostgresBookRepository();
    BookDataIntegration bookDataIntegration = new BookDataIntegration();
    BookFactory bookFactory = new BookFactory();
    BookService bookService = new BookService(bookRepository, bookDataIntegration, bookFactory);
    new BookController(bookService);

    CountryService countryService = new CountryService(bookService);
    new CountryController(countryService);
  }

  public static int getPort() {
    String port = System.getenv("PORT");
    if (port != null) {
      return Integer.parseInt(port);
    }
    return DEFAULT_PORT;
  }
}
