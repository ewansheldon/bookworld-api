package bookworld_api.acceptance;

import bookworld_api.App;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import spark.Spark;

public class CucumberHelper {

  @Before
  public void startServer() {
    App.main(new String[0]);
  }

  @After
  public void stopServer() {
    Spark.stop();
  }
}
