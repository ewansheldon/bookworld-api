package bookworld_api.integrations;

import static bookworld_api.web.JsonHandler.getJsonFrom;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class BooksApiTest {

  @Test
  void formats_book_response_as_expected() throws IOException, JSONException {
    JSONObject object = getJsonFrom("https://www.googleapis.com/books/v1/volumes?q=vile+bodies+evelyn+waugh");
    JSONObject book = object.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
    assertNotNull(book.getString("description"));
    assertNotNull(book.getJSONObject("imageLinks").getString("thumbnail"));
  }
}
