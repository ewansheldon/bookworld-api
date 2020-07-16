package bookworld_api.web;

import spark.Request;

public interface TokenAuthenticator {

  void authenticate(Request req);
}
