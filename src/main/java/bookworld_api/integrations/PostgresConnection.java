package bookworld_api.integrations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {
  private static Connection connection;

  public static Connection psqlConnection() {
    if (connection == null) {
      try {
        connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/bookworld", "user",
            "password"
        );
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return connection;
  }
}
