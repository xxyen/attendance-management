package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class AccountOperatorTest {
  @Test
  public void test_ParseAccountObjectWithPassword() {
    // Create a JSONObject with all required fields and password
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userid", "john123");
    jsonObject.put("password", "password123");
    jsonObject.put("type", "professor");
    jsonObject.put("unique_id", "PROF001");

    // Call parseAccountObject with the JSONObject
    AccountOperator accountOperator = new AccountOperator();
    Account account = accountOperator.parseAccountObject(jsonObject);

    // Perform assertions on the returned account
    assertNotNull(account);
    assertEquals("john123", account.getUserid());
    assertTrue(account.isCorrectPassword("password123"));
    assertEquals("professor", account.getAccountType());
    assertEquals("PROF001", account.getPersonalId());
  }

  @Test
  public void test_ParseAccountObjectWithoutPassword() {
    // Create a JSONObject with all required fields but without password
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userid", "john123");
    jsonObject.put("type", "professor");
    jsonObject.put("unique_id", "PROF001");

    // Call parseAccountObject with the JSONObject
    AccountOperator accountOperator = new AccountOperator();
    Account account = accountOperator.parseAccountObject(jsonObject);

    // Perform assertions on the returned account
    assertNotNull(account);
    assertEquals("john123", account.getUserid());
    assertTrue(account.isCorrectPassword("password"));
    assertEquals("professor", account.getAccountType());
    assertEquals("PROF001", account.getPersonalId());
  }

  @Test
  public void test_setUp() {
    AccountOperator accountOperator = new AccountOperator();
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
      assertEquals(expected.get(cnt), account.getPersonalId());
      assertEquals("professor", account.getAccountType());
      cnt ++;
    }
  }

}
