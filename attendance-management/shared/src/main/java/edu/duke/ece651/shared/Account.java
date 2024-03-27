package edu.duke.ece651.shared;

/**
 * represent any type of account
 */
public interface Account {
  /**
   * Get the˚user id
   */
  String getUserid();

  /**
   * Get the account type
   */
  String getAccountType();

  /**
   * Get the personal ID of the user
   */
  String getPersonalID();

  /**
   * Check if the password is correct
   */
  boolean isCorrectPassword(String pwd);

  /**
   * Get the encrypted password
   */
  String getEncryptedPwd() throws Exception;
}
