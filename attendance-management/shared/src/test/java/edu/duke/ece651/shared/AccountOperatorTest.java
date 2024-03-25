package edu.duke.ece651.shared;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class AccountOperatorTest {
  AccountOperator accountOperator = new AccountOperator("src/main/resources/");
  @Test
  public void test_ParseAccountObjectWithPassword() {
    // Create a JSONObject with all required fields and password
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userid", "john123");
    try {
      jsonObject.put("password", encrypt("password123"));
    } catch(Exception e) {
      System.out.println("AccountOperatorTest: failed to encrypt the password: " + e.getMessage());
    }
    jsonObject.put("type", "professor");
    jsonObject.put("unique_id", "PROF001");

    // Call parseAccountObject with the JSONObject
    Account account = accountOperator.parseAccountObject(jsonObject);

    // Perform assertions on the returned account
    assertNotNull(account);
    assertEquals("john123", account.getUserid());
    assertTrue(account.isCorrectPassword("password123"));
    assertEquals("professor", account.getAccountType());
    assertEquals("PROF001", account.getPersonalID());
  }

  @Test
  public void test_ParseAccountObjectWithoutPassword() {
    // Create a JSONObject with all required fields but without password
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userid", "john123");
    jsonObject.put("type", "professor");
    jsonObject.put("unique_id", "PROF001");
    try {
      jsonObject.put("password", encrypt(""));
    } catch(Exception e) {
      System.out.println("AccountOperatorTest: failed to encrypt the password: " + e.getMessage());
    }
    // Call parseAccountObject with the JSONObject
    Account account = accountOperator.parseAccountObject(jsonObject);

    // Perform assertions on the returned account
    assertNotNull(account);
    assertEquals("john123", account.getUserid());
    assertTrue(account.isCorrectPassword("password"));
    assertEquals("professor", account.getAccountType());
    assertEquals("PROF001", account.getPersonalID());
  }

  @Test
  public void test_setUp() {
    ArrayList<String> expected = new ArrayList<>();
    expected.add("Tyler");
    expected.add("something123");
    expected.add("tkb13");
    expected.add("Brian");
    expected.add("something234");
    expected.add("bmr23");
    expected.add("Drew");
    expected.add("password");
    expected.add("adh39");

    int cnt = 0;
    // "Tyler", "something123", "tkb13", "Brian", "something234", "bmr23", "Drew",
    // "password", "adh39"
    for (Account account : accountOperator.getAccounts()) {
      assertEquals(expected.get(cnt), account.getUserid());
      cnt++;
      assertTrue(account.isCorrectPassword(expected.get(cnt)));
      cnt++;
      assertEquals(expected.get(cnt), account.getPersonalID());
      assertEquals("professor", account.getAccountType());
      cnt ++;
    }
  }

  @Test
  public void test_createAccount_student() {
    assertNull(accountOperator.createAccount("ss", "", "student", "123", false));
    assertThrows(IllegalArgumentException.class, () -> accountOperator.createAccount("Drew", "", "professor", "123", false));
  }

  @Test
  public void test_signIn_professor() {
    Email email = new Email("xxx@xxx.com");
    User prof1 = new Professor("DH", "adh39", email);
    User prof2 = new Professor("TB", "tkb13", email);
    HashMap<String, User> profList = new HashMap<>();
    profList.put("adh39", prof1);
    assertThrows(IllegalArgumentException.class, () -> accountOperator.signIn("Tyler", "something123", profList));
    profList.put("tkb13", prof2);
    assertEquals(prof2, accountOperator.signIn("Tyler", "something123", profList));
    assertThrows(IllegalArgumentException.class, () -> accountOperator.signIn("David", "something123", profList));
    assertThrows(IllegalArgumentException.class, () -> accountOperator.signIn("Tyler", "something234", profList));
    System.out.println("55555");
  }

  private String encrypt(String strToEncrypt) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    SecretKeySpec keySpec = new SecretKeySpec("39edh*huis$iuh5yf@jf95jd".getBytes(), "AES");
    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }
  
  @Test
  public void test_temp() {
    String tempPath = "src/main/resources/decrypted2.json";
    try {
      //FileEncryptorDecryptor.encrypt(tempPath, "src/main/resources/AccountList.txt");
      //FileEncryptorDecryptor.decrypt("src/main/resources/AccountList.txt", "src/main/resources/accounts.json");
    }
    catch(Exception e) {
      
    }
   // use the tempPath to write file
// new File(tempPath).delete();

  }
}
