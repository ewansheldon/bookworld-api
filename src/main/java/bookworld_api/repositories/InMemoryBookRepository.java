package bookworld_api.repositories;

import bookworld_api.Book;
import java.util.ArrayList;
import java.util.List;

public class InMemoryBookRepository implements BookRepository {

  private List<Book> books;

  public InMemoryBookRepository() {
    books = new ArrayList<>();
  }

  public Book create(Book book) {
    books.add(book);
    return book;
  }
}
