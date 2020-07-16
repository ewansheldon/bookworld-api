package bookworld_api.web;

import bookworld_api.exceptions.UnauthorisedNicoUser;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Collections;
import spark.Request;

public class GoogleTokenAuthenticator implements TokenAuthenticator {

  public void authenticate(Request req)
      throws UnauthorisedNicoUser {
    try {
      String token = req.headers("Authorization");
      GoogleIdTokenVerifier verifier = buildGoogleIdTokenVerifier();
      GoogleIdToken idToken = verifier.verify(token);
      String email = idToken.getPayload().getEmail();
      if (!email.equals(System.getenv("EWAN")) && !email.equals(System.getenv("NICO"))) {
        throw new UnauthorisedNicoUser();
      }
    } catch(Exception e) {
      throw new UnauthorisedNicoUser();
    }
  }

  private GoogleIdTokenVerifier buildGoogleIdTokenVerifier() {
    JacksonFactory jsonFactory = new JacksonFactory();
    HttpTransport transport = new NetHttpTransport();
    return new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
        .setAudience(Collections.singletonList(System.getenv("GOOGLE_AUTH_CLIENT_ID")))
        .build();
  }
}
