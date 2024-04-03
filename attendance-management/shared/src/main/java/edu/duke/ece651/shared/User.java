package edu.duke.ece651.shared;

/**
 * reprsent any type of user in this system
 */
public interface User {
  /**
   * Get the˚user id
   */
  String getUserid();

  /**
   * get user type(e.g. "professor", "student")
   */
  String getUserType();

  /**
   * Check if the password is correct
   */
  boolean isCorrectPassword(String pwd);

  boolean saveToDB();
}
