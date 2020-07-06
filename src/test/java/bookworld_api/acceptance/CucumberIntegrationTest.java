package bookworld_api.acceptance;

import bookworld_api.App;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import spark.Spark;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features", strict = true)
public class CucumberIntegrationTest {
  @BeforeClass
  public static void startServer() {
    App.main(new String[0]);
  }

  @AfterClass
  public static void stopServer() {
    Spark.stop();
  }
}
