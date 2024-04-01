package edu.duke.ece651.shared;

/**
 * reprsent user in this system
 */
public interface User {
  /**
   * get user personal ID
   */
  String getPersonalID();

  /**
   * get user type(e.g. "professor", "student")
   */
  String getUserType();
}
