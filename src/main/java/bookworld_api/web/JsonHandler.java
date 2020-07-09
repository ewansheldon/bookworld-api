package bookworld_api.web;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHandler {

  private static Gson gson;

  public static String stringify(Object obj) {
    if (gson == null) {
      gson = new Gson();
    }

    return gson.toJson(obj);
  }

  public static JSONObject getJsonFrom(String address) throws IOException, JSONException {
    URL url = new URL(address);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer content = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }
    in.close();

    return new JSONObject(String.valueOf(content));
  }
}
