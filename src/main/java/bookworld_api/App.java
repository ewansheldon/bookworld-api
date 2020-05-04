package bookworld_api;

import spark.Spark;
import com.google.gson.Gson;

import java.util.HashMap;

import static spark.Spark.get;

public class App {

    public static final int DEFAULT_PORT = 80;

    public static void main(String[] strings) {
        Spark.port(getPort());
        get("books/:country_code", (req, res) -> {
            System.out.println("hello");
            res.type("application/json");
            return new Gson().toJson(bookObject());
        });
    }

    private static Object bookObject() {
        HashMap<String, String> res = new HashMap();
        res.put("title", "Vile Bodies");
        res.put("author", "Evelyn Waugh");
        return res;
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        if (port != null)
            return Integer.parseInt(port);
        return DEFAULT_PORT;
    }
}
