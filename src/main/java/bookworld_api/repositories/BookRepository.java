package bookworld_api.repositories;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.exceptions.InvalidBookException;
import bookworld_api.request_objects.UpdateBookRequestObject;
import java.sql.SQLException;
import java.util.List;

public interface BookRepository {

  Book create(Book book) throws SQLException;

  List<String> getCountries() throws SQLException;

  Book getByCountry(String country_code) throws CountryNotValidException, SQLException;

  List<Book> getAll() throws SQLException;

  Book update(Long id, UpdateBookRequestObject request) throws InvalidBookException;
}
