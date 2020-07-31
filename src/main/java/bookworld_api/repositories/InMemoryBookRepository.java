package bookworld_api.repositories;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.exceptions.InvalidBookException;
import bookworld_api.request_objects.UpdateBookRequestObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {

  private List<Book> books;
  private int id;

  public InMemoryBookRepository() {
    books = new ArrayList<>();
  }

  public Book create(Book book) {
    book = new Book(++id, book.getTitle(), book.getAuthor(), book.getCountry(),
        book.getDescription(), book.getThumbnail());
    books.add(book);
    return book;
  }

  public List<String> getCountries() {
    return books.stream().map(Book::getCountry).collect(Collectors.toList());
  }

  public Book getByCountry(String country_code) throws CountryNotValidException {
    Optional<Book> book = books.stream()
        .filter(b -> b.getCountry().toUpperCase().equals(country_code.toUpperCase())).findFirst();
    if (book.isEmpty()) {
      throw new CountryNotValidException();
    } else {
      return book.get();
    }
  }

  public List<Book> getAll() {
    return books;
  }

  public Book update(Long id, UpdateBookRequestObject request) throws InvalidBookException {
    Optional<Book> book = books.stream().filter(b -> b.getId() == id).findFirst();
    if (book.isEmpty()) {
      throw new InvalidBookException();
    } else {
      Book updatedBook = new Book(id, request.getTitle(), request.getAuthor(), request.getCountry(),
          request.getDescription(), request.getThumbnail());
      int index = books.indexOf(book.get());
      books.set(index, updatedBook);
      return updatedBook;
    }
  }
}
