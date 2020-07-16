package bookworld_api.web;

import bookworld_api.exceptions.UnauthorisedNicoUser;
import java.io.IOException;
import java.security.GeneralSecurityException;
import spark.Request;

public interface TokenAuthenticator {

  void authenticate(Request req) throws GeneralSecurityException, IOException, UnauthorisedNicoUser;
}
