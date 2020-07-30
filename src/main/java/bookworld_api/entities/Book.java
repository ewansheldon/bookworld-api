package bookworld_api.entities;

public class Book {

  private int id;
  private String title;
  private String author;
  private String country;
  private String description;
  private String thumbnail;

  public Book() {
  }

  public Book(String title, String author, String country, String description, String thumbnail) {
    this.title = title;
    this.author = author;
    this.country = country;
    this.description = description;
    this.thumbnail = thumbnail;
  }

  public Book(int id, String title, String author, String country, String description,
      String thumbnail) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.country = country;
    this.description = description;
    this.thumbnail = thumbnail;
  }

  public int getId() {
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
