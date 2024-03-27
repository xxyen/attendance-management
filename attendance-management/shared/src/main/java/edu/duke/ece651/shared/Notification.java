package edu.duke.ece651.shared;

public interface Notification {
  /**
   * Send different types of Notifications
   * @return true if successfully sent Notification, otherwise return false
   */
  public boolean sendNotification();
}
