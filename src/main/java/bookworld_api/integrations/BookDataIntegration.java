package bookworld_api.integrations;

import bookworld_api.request_objects.BookRequestObject;
import bookworld_api.web.JsonHandler;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class BookDataIntegration {

  public BookDataResponseObject get(BookRequestObject request) throws IOException, JSONException {
    String address = "https://www.googleapis.com/books/v1/volumes?q="
        + request.getTitle().replace(' ', '+') + "+"
        + request.getAuthor().replace(' ', '+');
    JSONObject book = JsonHandler.getJsonFrom(address).getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
    return new BookDataResponseObject(
        book.getString("description"),
        book.getJSONObject("imageLinks").getString("thumbnail")
    );
  }
}
