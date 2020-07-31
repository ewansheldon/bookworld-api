package bookworld_api.mappers;

import bookworld_api.entities.Book;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper {

  public Book mapToBook(ResultSet resultSet) throws SQLException {
    return new Book(
        resultSet.getLong("id"), resultSet.getString("title"), resultSet.getString("author"),
        resultSet.getString("country"), resultSet.getString("description"),
        resultSet.getString("thumbnail")
    );
  }
}