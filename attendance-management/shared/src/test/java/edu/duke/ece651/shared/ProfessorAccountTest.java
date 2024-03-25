package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProfessorAccountTest {
  @Test
  public void test_isCorrectPassword() {
    Account acc = new ProfessorAccount("userid", "profid", false);
    assertTrue(acc.isCorrectPassword("password"));
    assertFalse(acc.isCorrectPassword(""));
    assertFalse(acc.isCorrectPassword(null)); 
  }

  @Test
  public void test_xx() {
    try {
      ProfessorAccount acc = new ProfessorAccount("userid", "profid", false);
    String original = "something123";
    String encrypted = acc.encrypt(original);
    String decrypted = acc.decrypt(encrypted);
    assertEquals(original, decrypted); 
    System.out.println(original+ ", "+encrypted+", "+decrypted);
    }
    catch(Exception e) {
      System.out.println("111111111111!"+e.getMessage());
    }

  }
}
