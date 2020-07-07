package bookworld_api.util;

import spark.Spark;

public class Server {

  public static void stop() {
    Spark.stop();
    Spark.awaitStop();
  }
}
