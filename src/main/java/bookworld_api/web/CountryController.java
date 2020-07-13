package bookworld_api.web;

import static bookworld_api.web.JsonHandler.stringify;
import static spark.Spark.exception;
import static spark.Spark.get;

import bookworld_api.exceptions.CountryNotValidException;
import bookworld_api.services.CountryService;
import java.sql.SQLException;
import spark.Spark;

public class CountryController {

  private CountryService countryService;

  public CountryController(CountryService countryService) {
    this.countryService = countryService;
    createRoutes();
  }

  public void createRoutes() {
    get("countries", (req, res) -> {
      res.type("application/json");
      return stringify(countryService.getAll());
    });

    exception(SQLException.class, (e, req, res) -> {
      res.status(422);
      res.type("text/html");
      res.body(e.getMessage());
    });

    Spark.awaitInitialization();
  }
}
