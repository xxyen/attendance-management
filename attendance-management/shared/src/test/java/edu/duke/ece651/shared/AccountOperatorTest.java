package edu.duke.ece651.shared;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class AccountOperatorTest {
  AccountOperator accountOperator = new AccountOperator("src/main/resources/AccountList.json");
  @Test
  public void test_ParseAccountObjectWithPassword() {
    // Create a JSONObject with all required fields and password
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userid", "john123");
    jsonObject.put("password", "password123");
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
    assertNull(accountOperator.createAccount("ss", "", "student", "123"));
    assertThrows(IllegalArgumentException.class, () -> accountOperator.createAccount("Drew", "", "professor", "123"));
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
  }
  
}
