package bookworld_api.web;

import bookworld_api.exceptions.UnauthorisedNicoUser;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Collections;
import java.util.List;
import spark.Request;

public class GoogleTokenAuthenticator implements TokenAuthenticator {

  private List<String> allowedUsers;

  public GoogleTokenAuthenticator() {
    this.allowedUsers = createUserAllowlist();
  }

  private List<String> createUserAllowlist() {
    return List.of(System.getenv("EWAN"), System.getenv("NICO"), System.getenv("LUCIE"));
  }

  public void authenticate(Request req)
      throws UnauthorisedNicoUser {
    try {
      String token = req.headers("Authorization");
      GoogleIdTokenVerifier verifier = buildGoogleIdTokenVerifier();
      GoogleIdToken idToken = verifier.verify(token);
      String email = idToken.getPayload().getEmail();
      if (!allowedUsers.contains(email)) {
        throw new UnauthorisedNicoUser();
      }
    } catch (Exception e) {
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
