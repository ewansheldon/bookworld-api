package bookworld_api.request_objects;

public class BookRequestObject {

  private String title;
  private String author;
  private String country;

  public BookRequestObject(String title, String author, String country) {
    this.title = title;
    this.author = author;
    this.country = country;
  }

  public BookRequestObject() {
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
}
