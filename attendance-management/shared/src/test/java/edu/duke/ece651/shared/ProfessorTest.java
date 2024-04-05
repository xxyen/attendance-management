package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProfessorTest {
  Email email = new Email("aaaa@bbb.com");
  Professor prof = new Professor("A", "aa123", email);

  @Test
  public void test_getsAndSetters() {
    assertEquals("professor", prof.getUserType());
    assertEquals(email, prof.getEmail());
    // assertTrue(s1.setPwd("aaa123"));
    // assertFalse(s1.setPwd(""));
    // assertFalse(s1.isDefaultPwd());
  }
}
