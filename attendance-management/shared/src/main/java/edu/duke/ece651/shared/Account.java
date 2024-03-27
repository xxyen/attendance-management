package edu.duke.ece651.shared;

public interface Account {
  String getUserid();
  String getAccountType();
  String getPersonalID();
  boolean isCorrectPassword(String pwd);
  String getEncryptedPwd() throws Exception;
}
