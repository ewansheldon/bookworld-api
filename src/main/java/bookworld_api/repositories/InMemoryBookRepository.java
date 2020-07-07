package bookworld_api.repositories;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

  public Book getBookByCountry(String country_code) throws CountryNotValidException {
    Optional<Book> book = books.stream().filter(b -> b.getCountry().toLowerCase().equals(country_code.toLowerCase())).findFirst();
    if (book.isEmpty()) {
      throw new CountryNotValidException();
    } else {
      return book.get();
    }
  }
}
