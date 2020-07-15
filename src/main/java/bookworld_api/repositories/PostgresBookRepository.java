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

  private final String BOOKS_LIST = "SELECT * FROM books;";
  private final String COUNTRIES_LIST = "SELECT DISTINCT country FROM books;";
  private final String BOOK_BY_COUNTRY = "SELECT * FROM books WHERE country = ? LIMIT 1;";
  private final String CREATE_BOOK = "INSERT INTO books (title,author,country,description,thumbnail) "
      + "VALUES(?,?,?,?,?);";

  public Book create(Book book) throws SQLException {
    PreparedStatement preparedStatement = psqlConnection().prepareStatement(CREATE_BOOK);
    preparedStatement.setString(1, book.getTitle());
    preparedStatement.setString(2, book.getAuthor());
    preparedStatement.setString(3, book.getCountry());
    preparedStatement.setString(4, book.getDescription());
    preparedStatement.setString(5, book.getThumbnail());
    preparedStatement.executeUpdate();
    return book;
  }

  public List<String> getCountries() throws SQLException {
    List<String> countries = new ArrayList<>();
    ResultSet resultSet = psqlConnection().createStatement().executeQuery(COUNTRIES_LIST);
    while (resultSet.next()) {
      countries.add(resultSet.getString("country"));
    }
    return countries;
  }

  public Book getByCountry(String country_code) throws CountryNotValidException, SQLException {
    PreparedStatement preparedStatement = psqlConnection().prepareStatement(BOOK_BY_COUNTRY);
    preparedStatement.setString(1, country_code.toUpperCase());
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

  public List<Book> getAll() throws SQLException {
    List<Book> books = new ArrayList<>();
    ResultSet resultSet = psqlConnection().createStatement().executeQuery(BOOKS_LIST);
    while (resultSet.next()) {
      books.add(new Book(
          resultSet.getString("title"), resultSet.getString("author"),
          resultSet.getString("country"), resultSet.getString("description"),
          resultSet.getString("thumbnail")
      ));
    }

    return books;
  }

}
