package edu.duke.ece651.shared;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;


public class EmailNotification implements Notification {
  private Email fromAddress;
  private Email toAddress;

  public EmailNotification(Email fromAddress, Email toAddress) {
    this.fromAddress = fromAddress;
    this.toAddress = toAddress;
  }

  /**
   * Application name.
   */
  private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
  /**
   * Global instance of the JSON factory.
   */
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  /**
   * Directory to store authorization tokens for this application.
   */
  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  /**
   * Global instance of the scopes required by this quickstart.
   * If modifying these scopes, delete your previously saved tokens/ folder.
   */
  //private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_LABELS);
  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  @Override
  public boolean sendNotification() {
    return false;
  }

  /**
   * Creates an authorized Credential object.
   *
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */
  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
          throws IOException {
    // Load client secrets.
    InputStream in = EmailNotification.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }
    GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    //returns an authorized Credential object.
    return credential;
  }

  /**
   * Send a single Email
   * @param subject is the subject of the Email
   * @param bodyText is the body contents of the Email
   */
  public void sendEmail(String subject, String bodyText) throws IOException, GeneralSecurityException {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();

    String rawEmailString = createRawEmailString(toAddress.getEmailAddr(), "me", subject, bodyText);
    Message message = createMessage(rawEmailString);
    sendMessage(service, "me", message);
  }

  /**
   * Create a String containing the entire Email
   * @param to is the receiver Email
   * @param from is the sender Email
   * @param subject is the subject of the Email
   * @param body is the body contents of the Email
   * @return the String containing all information
   */
  private static String createRawEmailString(String to, String from, String subject, String body) {
    String bodyText = "Content-Type: text/plain; charset=\"UTF-8\"\n" +
            "MIME-Version: 1.0\n" +
            "Content-Transfer-Encoding: 7bit\n" +
            "to: " + to + "\n" +
            "from: " + from + "\n" +
            "subject: " + subject + "\n\n" +
            body;

    return Base64.getUrlEncoder().encodeToString(bodyText.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Create a Message from the raw Email String
   * @param rawEmail is the String containing the entire Email
   * @return the formatted Message to send
   */
  private static Message createMessage(String rawEmail) {
    Message message = new Message();
    message.setRaw(rawEmail);
    return message;
  }

  /**
   * Perform the sending Email operation
   * @param userId is the ID of the sender
   * @param email is the Email to send in Message format
   */
  private static void sendMessage(Gmail service, String userId, Message email) throws IOException {
    service.users().messages().send(userId, email).execute();
  }


}
