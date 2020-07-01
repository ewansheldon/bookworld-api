package bookworld_api.web;

import static spark.Spark.get;
import static spark.Spark.post;

import bookworld_api.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class BookController {
  public void routes() {
    post("/books", (req, res) -> {
      ObjectMapper objectMapper = new ObjectMapper();
      Book book = objectMapper.readValue(req.body(), Book.class);
      res.status(201);
      return book;
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
