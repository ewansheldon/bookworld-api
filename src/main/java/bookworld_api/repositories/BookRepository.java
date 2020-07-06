package bookworld_api.repositories;

import bookworld_api.Book;
import java.util.List;

public interface BookRepository {

  Book create(Book book);

  List<String> getCountries();
}
