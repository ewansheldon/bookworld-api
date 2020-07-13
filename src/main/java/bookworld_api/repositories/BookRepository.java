package bookworld_api.repositories;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import java.sql.SQLException;
import java.util.List;

public interface BookRepository {

  Book create(Book book) throws SQLException;

  List<String> getCountries() throws SQLException;

  Book getBookByCountry(String country_code) throws CountryNotValidException, SQLException;
}
