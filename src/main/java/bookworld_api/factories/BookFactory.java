package bookworld_api.factories;

import bookworld_api.entities.Book;
import bookworld_api.integrations.BookDataResponseObject;
import bookworld_api.request_objects.BookRequestObject;

public class BookFactory {

  public Book create(BookRequestObject request, BookDataResponseObject bookDataResponse) {
    return new Book(request.getTitle(), request.getAuthor(), request.getCountry(), bookDataResponse.description, bookDataResponse.thumbnail);
  }
}
