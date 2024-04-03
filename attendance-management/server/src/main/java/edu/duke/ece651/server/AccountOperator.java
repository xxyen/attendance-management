// package edu.duke.ece651.server;

// import java.io.File;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.util.ArrayList;
// import java.util.Map;
// import java.util.Optional;

// import org.json.JSONArray;
// import org.json.JSONObject;
// import org.json.JSONTokener;

// import java.sql.*;
 

// /**
//  * This class is for managing all accounts of this system.
//  * Including import accounts from file and some other account interaction for the system
//  */
// public class AccountOperator {
//   private final Connection connection;
//   //private ArrayList<Account> accounts;
//   /**
//    * Construct an account operator by loading from a encrypted file
//    */
//   public AccountOperator(){                                                                    
//     this.connection = JDBCUtils.getConnection();
//   }


//   /**
//    * Create an account
//    */
//   public Account createAccount(String userid, String password, String type, String id) {
//     if (useridExists(userid)) {
//       throw new IllegalArgumentException("userid exists!");
//     }
//     if(type.equals("professor")){
//       return new ProfessorAccount(userid, password, id, true);
//     } else if(type.equals("student")){
      
//     }
//     //no other type of account for now
//     return null;
//   }

//   /**
//    * Check if one userid exists
//    */
//   private boolean useridExists(String userid) {
//     //??? access DB
//     return false;
//   }

//   public void signUp() {
//     // is the personal id linked to a account already?
//     // if not, get the user type
//     // does the userid exist?
//     // if not, create a new account
//     // save it to the DB
//   }

  
//   private void saveAccounts() {
    

//   }


//   /**
//    * Get the user personal ID of an account if the userid and password are verified correct
//    */
//   public String getAccountPersonalID(String userid, String password) {
//     //??? access DB
//     return null;
//   }

//   public boolean isAdmin(String userid, String password){
//     if((userid.equals(adminID) && (password.equals(adminPwd)))) {
//       return true;
//     }
//     return false;
//   }
// }
