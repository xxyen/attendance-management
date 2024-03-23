package edu.duke.ece651.shared;

public interface Account {
  String getUserid();
  String getAccountType();
  String getPersonalId();
  boolean isCorrectPassword(String pwd);
}
