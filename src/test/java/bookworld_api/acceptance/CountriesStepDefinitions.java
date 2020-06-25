package bookworld_api.acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CountriesStepDefinitions {

  @Given("there is a book from {string}")
  public void thereIsABookFrom(String country) {
    System.out.println("one");
    System.out.println(country);
  }

  @When("the user fetches all countries")
  public void theUserFetchesAllCountries() {
    System.out.println("two");
  }

  @Then("the user receives {string} in the list of countries")
  public void theUserReceivesInTheListOfCountries(String country) {
    System.out.println("three");
    assertTrue(false);
  }
}
