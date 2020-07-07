package bookworld_api.web;

import static spark.Spark.get;

import bookworld_api.services.CountryService;
import com.google.gson.Gson;
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
      return new Gson().toJson(countryService.getAll());
    });

    Spark.awaitInitialization();
  }
}
