package bookworld_api.repositories;

import bookworld_api.entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {

  private List<Book> books;

  public InMemoryBookRepository() {
    books = new ArrayList<>();
  }

  public Book create(Book book) {
    books.add(book);
    return book;
  }

  public List<String> getCountries() {
    return books.stream().map(Book::getCountry).collect(Collectors.toList());
  }
}
