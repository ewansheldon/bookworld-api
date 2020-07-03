package bookworld_api.services;

import bookworld_api.Book;
import bookworld_api.repositories.BookRepository;
import java.util.List;

public class BookService {

  private BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Book create(Book book) {
    return bookRepository.create(book);
  }

  public List<String> getCountries() {
    throw new UnsupportedOperationException();
  }
}
