package bookworld_api.web;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
      Book book = objectMapper.readValue(req.body(), Book.class);
      res.status(201);
      return new Gson().toJson(bookService.create(book));
    });

    get("books/:country_code", (req, res) -> {
      res.type("application/json");
      System.out.println(req.params("country_code"));
      Book book = bookService.getBookFrom(req.params("country_code"));
      return new Gson().toJson(book);
    });

    exception(CountryNotValidException.class, (e, req, res) -> {
      res.status(422);
      res.type("text/html");
      res.body(e.getMessage());
    });

    Spark.awaitInitialization();
  }
}
