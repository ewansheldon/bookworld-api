Feature: getting the list of countries

  Scenario: user fetches all countries
    Given there is a book from "GBR"
    When the user fetches all countries
    Then the user receives "GBR" in the list of countries