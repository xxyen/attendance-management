package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProfessorTest {
  Email email = new Email("aaaa@bbb.com");
  Professor prof = new Professor("A", "aa123", email);
  
  @Test
  public void test_getUserType() {
    assertEquals("professor", prof.getUserType());
  }

  @Test
  public void test_getEmail() {
    assertEquals(email, prof.getEmail());
  }

}
