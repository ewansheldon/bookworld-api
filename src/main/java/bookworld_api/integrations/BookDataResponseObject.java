package bookworld_api.integrations;

public class BookDataResponseObject {

  public final String description;
  public final String thumbnail;

  public BookDataResponseObject(String description, String thumbnail) {
    this.description = description;
    this.thumbnail = thumbnail;
  }
}
