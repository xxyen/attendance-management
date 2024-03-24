package edu.duke.ece651.shared;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileWriter;

public class AccountOperator {
  private ArrayList<Account> accounts;
  private String filePath;
  
  public AccountOperator(String importPath){                                                                    
    this.filePath = importPath;
    this.accounts = importAccountsfromFile();
  }

  public Iterable<Account> getAccounts(){
    return accounts;
  }
  
  public ArrayList<Account> importAccountsfromFile() {
    ArrayList<Account> importedAccounts = new ArrayList<>();
    
    try {
      // Create a FileReader for the JSON file
      FileReader reader = new FileReader(filePath);
      
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
      //e.printStackTrace();
      System.out.println("Error occurs when importing accounts from the file: " + e.getMessage());
    }
    return importedAccounts;
  }
  
  public Account parseAccountObject(JSONObject obj) {
    JSONObject jsonObject = (JSONObject) obj;
    String userid = (String) jsonObject.get("userid");
    String password = (String) jsonObject.optString("password", "");
    String type = (String) jsonObject.get("type");
    String uniqueId = (String) jsonObject.get("unique_id");
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
      //      if (account == null) {
      // continue;
      //}
      if (userid.equals(account.getUserid())) {
        return true;
      }
    }
    return false;
  }
  
  private void saveAccounts() {
    JSONArray jsonArray = new JSONArray();
    for(Account account: accounts) {
      JSONObject accountObj = new JSONObject();
      accountObj.put("userid", account.getUserid());
      accountObj.put("password", account.getEncryptedPwd());
      accountObj.put("type", account.getAccountType());
      accountObj.put("unique_id", account.getPersonalID());
      jsonArray.put(accountObj);
    }

    try (FileWriter fileWriter = new FileWriter(filePath)) {
      fileWriter.write(jsonArray.toString(4)); // Use 4 spaces for indentation
      System.out.println("JSON file created successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

  // return user???
  public User signIn(String userid, String password, Map<String, User> userList) {
    Account account = accounts.stream().filter(acc -> acc.getUserid().equals(userid)).findFirst().orElse(null);
    if(account == null) {
      throw new IllegalArgumentException("This account doesn't exist!");
    }
    else {
      if(account.isCorrectPassword(password)){
        //find the professor in json file
        Optional<Map.Entry<String, User>> userEntryOptional = userList.entrySet().stream().filter(entry -> entry.getKey().equals(account.getPersonalID())).findFirst();
        if(userEntryOptional.isPresent()) {
          return userEntryOptional.get().getValue();
        }
        throw new IllegalArgumentException("User not found in userList map!");
      }
      else {
        throw new IllegalArgumentException("Incorrect password!");
      }
    }
  }
}
