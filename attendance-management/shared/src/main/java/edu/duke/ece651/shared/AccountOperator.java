package edu.duke.ece651.shared;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class AccountOperator {
  private ArrayList<Account> accounts;
  private String filePath;
  private static final String ALGORITHM = "AES";                                                                                                                                                                                                                         
  private static final String KEY = "39edh*huis$iuh5yf@jf95jd";
  
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
      String decryptedPath = filePath + "accounts.json";
      FileEncryptorDecryptor.decrypt(filePath+"AccountList.txt", decryptedPath);
      
      // Create a FileReader for the JSON file
      FileReader reader = new FileReader(decryptedPath);
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
      new File(decryptedPath).delete(); 
     
      //} catch (IOException | JSONException e) {
       } catch (Exception e) {
      System.out.println("Error occurs when importing accounts from the file: " + e.getMessage());
    }
    return importedAccounts;
  }
  
  public Account parseAccountObject(JSONObject obj) {
    try {
      JSONObject jsonObject = (JSONObject) obj;
      String userid = (String) jsonObject.get("userid");
      //String encryptedPwd = (String) jsonObject.optString("password", "");
      //String password = (encryptedPwd.equals(""))? "" : decryptPwd(encryptedPwd);
      String password = (String) jsonObject.optString("password", "PoBcbzFUh+htYsP0aIMXeg==");       
      String type = (String) jsonObject.get("type");
      String uniqueId = (String) jsonObject.get("unique_id");
      return createAccount(userid, password, type, uniqueId, true);
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    throw new IllegalArgumentException("Something went wrong when parsing the account object!");
  }
  
  public Account createAccount(String userid, String password, String type, String id, boolean encryptedPwd) {
    if (useridExists(userid)) {
      throw new IllegalArgumentException("userid exists!");
    }
    if(type.equals("professor")){
      return new ProfessorAccount(userid, password, id, true);
    }
    //no other type of account for now
    return null;
  }
  
  public boolean useridExists(String userid) {
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

  // for accounts change late, not for evo1
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
    
    String originalFile = filePath + "accounts.json";
    try (FileWriter fileWriter = new FileWriter(originalFile)) {
      fileWriter.write(jsonArray.toString(4)); // Use 4 spaces for indentation
      //System.out.println("JSON file created successfully.");
      String encryptedFile = filePath + "AccountList.txt";      
      FileEncryptorDecryptor.encrypt(originalFile, encryptedFile);
      //(new File(originalFile)).delete();


    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  public String getAccountPersonalID(String userid, String password) {
    Account account = accounts.stream().filter(acc -> acc.getUserid().equals(userid)).findFirst().orElse(null);
    if(account != null && account.isCorrectPassword(password)){
      return account.getPersonalID();
    }
    return null;
  }
  
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
