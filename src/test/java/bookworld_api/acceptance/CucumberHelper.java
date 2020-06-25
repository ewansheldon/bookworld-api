package bookworld_api.acceptance;

import bookworld_api.App;
import io.cucumber.java.Before;

public class CucumberHelper {

  @Before
  public void startServer() {
    App.main(new String[0]);
  }
}
