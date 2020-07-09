package bookworld_api.web;

import static bookworld_api.web.JsonHandler.stringify;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.request_objects.BookRequestObject;
import bookworld_api.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Spark;

public class BookController {

  private BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
    createRoutes();
  }

  public void createRoutes() {
    post("/books", (req, res) -> {
      res.type("application/json");
      ObjectMapper objectMapper = new ObjectMapper();
      BookRequestObject request = objectMapper.readValue(req.body(), BookRequestObject.class);
      System.out.println(request);
      res.status(201);
      return stringify(bookService.create(request));
    });

    get("books/:country_code", (req, res) -> {
      res.type("application/json");
      Book book = bookService.getBookFrom(req.params("country_code"));
      return stringify(book);
    });

    exception(CountryNotValidException.class, (e, req, res) -> {
      res.status(422);
      res.type("text/html");
      res.body(e.getMessage());
    });

    Spark.awaitInitialization();
  }
}
