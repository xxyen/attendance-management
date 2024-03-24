package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProfessorAccountTest {
  @Test
  public void test_isCorrectPassword() {
    Account acc = new ProfessorAccount("userid", "profis");
    assertTrue(acc.isCorrectPassword("password"));
    assertFalse(acc.isCorrectPassword(""));
    assertFalse(acc.isCorrectPassword(null)); 
  }

}
