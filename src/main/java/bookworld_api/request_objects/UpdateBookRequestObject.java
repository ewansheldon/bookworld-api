package bookworld_api.request_objects;

public class UpdateBookRequestObject {

  private long id;
  private String title;
  private String author;
  private String country;
  private String description;
  private String thumbnail;

  public UpdateBookRequestObject(long id, String title, String author, String country,
      String description, String thumbnail) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.country = country;
    this.description = description;
    this.thumbnail = thumbnail;
  }

  public UpdateBookRequestObject() {
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
