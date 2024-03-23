package edu.duke.ece651.shared;

public interface Account {
  String getUserid();
  String getAccountType();
  String getPersonalid();
  boolean isCorrectPassword(String pwd);
  void saveAccounts();
}
