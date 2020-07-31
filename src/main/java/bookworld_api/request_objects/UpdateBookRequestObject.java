package bookworld_api.request_objects;

public class UpdateBookRequestObject {

  private final long id;
  private final String title;
  private final String author;
  private final String country;
  private final String description;
  private final String thumbnail;

  public UpdateBookRequestObject(long id, String title, String author, String country,
      String description, String thumbnail) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.country = country;
    this.description = description;
    this.thumbnail = thumbnail;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getCountry() {
    return country;
  }

  public String getDescription() {
    return description;
  }

  public String getThumbnail() {
    return thumbnail;
  }
}
