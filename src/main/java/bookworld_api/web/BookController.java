package bookworld_api.web;

import static spark.Spark.get;
import static spark.Spark.post;

import bookworld_api.Book;
import bookworld_api.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class BookController {

  private BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  public void routes() {
    post("/books", (req, res) -> {
      res.type("application/json");
      ObjectMapper objectMapper = new ObjectMapper();
      Book book = objectMapper.readValue(req.body(), Book.class);
      res.status(201);
      return new Gson().toJson(bookService.create(book));
    });

    get("books/:country_code", (req, res) -> {
      res.type("application/json");
      return new Gson().toJson(bookObject());
    });
  }

  private static Book bookObject() {
    return new Book("Vile Bodies", "Evelyn Waugh", "1930", "GBR");
  }
}
