package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProfessorTest {
  Email email = new Email("aaaa@bbb.com");
  String pwd = "aa123";
  Professor prof = new Professor("A", pwd, email);

  @Test
  public void test_getsAndSetters() {
    assertEquals("professor", prof.getUserType());
    assertEquals(email, prof.getEmail());
  }

  @Test
  public void test_pwd() {
    prof.setPwd("");
    prof.setPwd(pwd);
    prof.isCorrectPassword("pwd");
    prof.isDefaultPwd();
    prof.isCorrectPassword(null);
    prof.isCorrectPassword(pwd);
  }
}
