package bookworld_api;

public class Book {

  private String title;
  private String author;
  private String publicationDate;
  private String country;

  public Book(String title, String author, String publicationDate, String country) {
    this.title = title;
    this.author = author;
    this.publicationDate = publicationDate;
    this.country = country;
  }

  public Book() {
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getPublicationDate() {
    return publicationDate;
  }

  public String getCountry() {
    return country;
  }
}
