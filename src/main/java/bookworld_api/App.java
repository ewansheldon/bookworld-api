package bookworld_api;

import static spark.Spark.after;
import static spark.Spark.get;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import spark.Filter;
import spark.Spark;

public class App {

  public static final int DEFAULT_PORT = 8080;

  public static void main(String[] strings) {
    Spark.port(getPort());

    after((Filter) (request, response) -> {
      response.header("Access-Control-Allow-Origin", "*");
      response.header("Access-Control-Allow-Methods", "GET");
    });

    get("books/:country_code", (req, res) -> {
      res.type("application/json");
      return new Gson().toJson(bookObject());
    });

    get("countries", (req, res) -> {
      res.type("application/json");
      return new Gson().toJson(List.of("GBR", "PRT", "AUS"));
    });
  }

  private static Object bookObject() {
    HashMap<String, String> res = new HashMap();
    res.put("title", "Vile Bodies");
    res.put("author", "Evelyn Waugh");
    res.put("publication_date", "1930");
    return res;
  }

  private static int getPort() {
    String port = System.getenv("PORT");
      if (port != null) {
          return Integer.parseInt(port);
      }
    return DEFAULT_PORT;
  }
}
