package edu.duke.ece651.shared;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

@Disabled 
public class AccountOperatorTest {
  AccountOperator accountOperator = new AccountOperator("src/main/resources/");

  @Test
  public void test_ParseAccountObjectWithPassword() {
    // Create a JSONObject with all required fields and password
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userid", "john123");

    jsonObject.put("password", "JpmyR3nBDFKPylL2++48vA=="); // encrypted "password123"

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

    jsonObject.put("password", "PoBcbzFUh+htYsP0aIMXeg=="); // encrypted ""

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
  public void testParseAccountObject_Exception() {
    // Create a JSONObject with invalid account data (missing required fields)
    JSONObject jsonObject = new JSONObject();
    // IllegalArgumentException
    assertThrows(IllegalArgumentException.class, () -> {
      accountOperator.parseAccountObject(jsonObject);
    });
  }

  @Test
  public void test_importAccountsfromFile_Exception() {
    AccountOperator accountOperator = new AccountOperator("invalid/");
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
      cnt++;
    }
  }

  @Test
  public void test_createAccount_student() {
    assertNull(accountOperator.createAccount("ss", "", "student", "123", false));
    assertThrows(IllegalArgumentException.class,
        () -> accountOperator.createAccount("Drew", "", "professor", "123", false));
  }

  @Test
  public void test_getAccountPersonalID() {
    assertEquals("adh39", accountOperator.getAccountPersonalID("Drew", "password"));
    assertEquals("tkb13", accountOperator.getAccountPersonalID("Tyler", "something123"));
    assertEquals("bmr23", accountOperator.getAccountPersonalID("Brian", "something234"));
    assertNull(accountOperator.getAccountPersonalID("Brian", "something"));
    assertNull(accountOperator.getAccountPersonalID("B", "something234"));
  }

  @Test
  public void test_temp() {
    String tempPath = "src/main/resources/decrypted2.json";
    try {
      // FileEncryptorDecryptor.encrypt(tempPath,
      // "src/main/resources/AccountList.txt");
      // FileEncryptorDecryptor.decrypt("src/main/resources/AccountList.txt",
      // "src/main/resources/accounts.json");
    } catch (Exception e) {

    }
    // use the tempPath to write file
    // new File(tempPath).delete();

  }
}
