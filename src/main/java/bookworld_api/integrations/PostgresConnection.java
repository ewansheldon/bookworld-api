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
            System.getenv("DB_URL"), System.getenv("DB_USER"),
            System.getenv("DB_PASSWORD")
        );
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return connection;
  }
}
