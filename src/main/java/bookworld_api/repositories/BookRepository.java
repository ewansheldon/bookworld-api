package bookworld_api.repositories;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import java.util.List;

public interface BookRepository {

  Book create(Book book);

  List<String> getCountries();

  Book getBookByCountry(String country_code) throws CountryNotValidException;
}
