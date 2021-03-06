package bookworld_api.web;

import static bookworld_api.web.JsonHandler.stringify;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.post;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.exceptions.UnauthorisedNicoUser;
import bookworld_api.request_objects.BookRequestObject;
import bookworld_api.request_objects.UpdateBookRequestObject;
import bookworld_api.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.management.remote.JMXAuthenticator;
import spark.Spark;

public class BookController {

  private BookService bookService;
  private TokenAuthenticator tokenAuthenticator;

  public BookController(BookService bookService, TokenAuthenticator tokenAuthenticator) {
    this.bookService = bookService;
    this.tokenAuthenticator = tokenAuthenticator;
    createRoutes();
  }

  public void createRoutes() {
    get("/books/:country_code", (req, res) -> {
      res.type("application/json");
      Book book = bookService.getBookFrom(req.params("country_code"));
      return stringify(book);
    });

    get("/books", (req, res) -> {
      tokenAuthenticator.authenticate(req);
      res.type("application/json");
      List<Book> books = bookService.getAllBooks();
      return stringify(books);
    });

    post("/books", (req, res) -> {
      tokenAuthenticator.authenticate(req);
      res.type("application/json");
      ObjectMapper objectMapper = new ObjectMapper();
      BookRequestObject request = objectMapper.readValue(req.body(), BookRequestObject.class);
      res.status(201);
      return stringify(bookService.create(request));
    });

    patch("/books/:id", (req, res) -> {
      tokenAuthenticator.authenticate(req);
      res.type("application/json");
      ObjectMapper objectMapper = new ObjectMapper();
      UpdateBookRequestObject request = objectMapper.readValue(req.body(), UpdateBookRequestObject.class);
      Book book = bookService.update(Long.parseLong(req.params("id")), request);
      return stringify(book);
    });

    exception(CountryNotValidException.class, (e, req, res) -> {
      res.status(422);
      res.type("text/html");
      res.body(e.getMessage());
    });

    exception(UnauthorisedNicoUser.class, (e, req, res) -> {
      res.status(401);
      res.type("text/html");
      res.body("Unauthorised");
    });

    Spark.awaitInitialization();
  }
}
