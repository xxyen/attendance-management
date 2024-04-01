package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import jdk.jfr.Timestamp;

public class ProfessorAccountTest {
  @Test
  public void test_isCorrectPassword() {
    Account acc1 = new ProfessorAccount("userid", "profid", false);
    assertTrue(acc1.isCorrectPassword("password"));
    assertFalse(acc1.isCorrectPassword(""));
    assertFalse(acc1.isCorrectPassword(null));

    Account acc2 = new ProfessorAccount("userid", "PoBcbzFUh+htYsP0aIMXeg==", "profid", true);
    assertFalse(acc1.isCorrectPassword(""));
    assertTrue(acc1.isCorrectPassword("password"));
  }

  @Test
  public void test_getEncryptedPwd() throws Exception{
    Account acc = new ProfessorAccount("userid", "profid", false);
    assertEquals("nmydbJUzifJafqZnlx/6OA==", acc.getEncryptedPwd());
  }

  @Test
  public void test_createProfessorAccount_Exception() {
    Account acc = new ProfessorAccount("userid", "aaa", "profid", true);
  }
  
    
  /*
   * @Test
   * public void test_xx() {
   * try {
   * ProfessorAccount acc = new ProfessorAccount("userid", "profid", false);
   * String original = "";
   * String encrypted = acc.encrypt(original);
   * String decrypted = acc.decrypt(encrypted);
   * assertEquals(original, decrypted);
   * //assertEquals(encrypted, decrypted);
   * //System.out.println(original+ ", "+encrypted+", "+decrypted);
   * }
   * catch(Exception e) {
   * System.out.println("111111111111!"+e.getMessage());
   * }
   * 
   * }
   */
}
