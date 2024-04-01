package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.jupiter.api.Disabled;

@Disabled
public class EmailNotificationTest {
  @Test
  public void test_send_email() throws IOException, GeneralSecurityException {
    Email eF = new Email("jzsun00@gmail.com");
    Email eT = new Email("cp357@duke.edu");
    EmailNotification sender = new EmailNotification(eF, eT);
    sender.sendEmail("Subject of the Test.", "Body of the test email.");
  }

}
