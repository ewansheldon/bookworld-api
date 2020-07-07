package bookworld_api.exceptions;

public class CountryNotValidException extends Exception {

  public CountryNotValidException() {
    super("Requested country is not valid");
  }
}
