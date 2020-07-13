package bookworld_api.repositories;

import static bookworld_api.integrations.PostgresConnection.psqlConnection;

import bookworld_api.entities.Book;
import bookworld_api.exceptions.CountryNotValidException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresBookRepository implements BookRepository {

  private final String COUNTRIES_LIST = "SELECT DISTINCT country FROM books;";
  private final String BOOK_BY_COUNTRY = "SELECT * FROM books WHERE country = ? LIMIT 1;";

  public Book create(Book book) {
    return null;
  }

  public List<String> getCountries() throws SQLException {
    ArrayList<String> countries = new ArrayList<>();
    ResultSet resultSet = psqlConnection().createStatement().executeQuery(COUNTRIES_LIST);
    while (resultSet.next()) {
      countries.add(resultSet.getString("country"));
    }
    return countries;
  }

  public Book getBookByCountry(String country_code) throws CountryNotValidException, SQLException {
    PreparedStatement preparedStatement = psqlConnection().prepareStatement(BOOK_BY_COUNTRY);
    preparedStatement.setString(1, country_code);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
      return new Book(
          resultSet.getString("title"), resultSet.getString("author"),
          resultSet.getString("country"), resultSet.getString("description"),
          resultSet.getString("thumbnail")
      );
    } else {
      throw new CountryNotValidException();
    }
  }

}
