package edu.duke.ece651.shared;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
 

public class AccountOperator {
  private ArrayList<Account> accounts;
  
  public AccountOperator(){                                                                    
    this.accounts = importAccountsfromFile();
  }

  public Iterable<Account> getAccounts(){
    return accounts;
  }
  
  public ArrayList<Account> importAccountsfromFile() {
    ArrayList<Account> importedAccounts = new ArrayList<>();
    
    try {
      // Create a FileReader for the JSON file
      FileReader reader = new FileReader("src/main/resources/AccountList.json");
      
      // Parse the FileReader using JSONTokener
      JSONTokener tokener = new JSONTokener(reader);
      
      JSONArray jsonArray = new JSONArray(tokener); // For parsing JSON arrays
      
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        Account newAcc = parseAccountObject(jsonObject);
        importedAccounts.add(newAcc);
      }
      // Close the reader
      reader.close();
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
    return importedAccounts;
  }
  
  public Account parseAccountObject(JSONObject obj) {
    JSONObject jsonObject = (JSONObject) obj;
    String userid = (String) jsonObject.get("userid");
    String password = (String) jsonObject.optString("password", "");
    String type = (String) jsonObject.get("type");
    String uniqueId = (String) jsonObject.get("unique_id");
    System.out.println(userid+", "+type);
    return createAccount(userid, password, type, uniqueId);
  }
  
  public Account createAccount(String userid, String password, String type, String id) {
    if (useridExists(userid)) {
      throw new IllegalArgumentException("userid exists!");
    }
    if(type.equals("professor")){
      return new ProfessorAccount(userid, password, id);
    }
    //no other type of account for now
    return null;
  }
  
  private boolean useridExists(String userid) {
    if (accounts == null) {
      return false;
    }
    for (Account account : accounts) {
      if (account == null) {
        continue;
      }
      if (userid == account.getUserid()) {
        return true;
      }
    }
    return false;
  }
  
  private void saveAccounts() {
    
  }

  // return user???
  public boolean signIn(String userid, String password) {
    Account account = accounts.stream().filter(acc -> acc.getUserid().equals(userid)).findFirst().orElse(null);
    if(account == null) {
      throw new IllegalArgumentException("This account doesn't exist!");
    }
    else {
      if(account.isCorrectPassword(password)){
        //find the professor in json file
        return true;
      }
      else {
        throw new IllegalArgumentException("Incorrect password!");
      }
    }
  }
}
