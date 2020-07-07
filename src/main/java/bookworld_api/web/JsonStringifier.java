package bookworld_api.web;

import com.google.gson.Gson;

public class JsonStringifier {

  private static Gson gson;

  public static String stringify(Object obj) {
    if (gson == null) {
      gson = new Gson();
    }

    return gson.toJson(obj);
  }
}
