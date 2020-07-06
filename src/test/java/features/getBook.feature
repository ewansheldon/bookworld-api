Feature: getting a random book for a country

  Scenario: user requests a book from a country
    Given there is a book from "GBR"
    When the user requests a book from "GBR"
    Then the user receives the book