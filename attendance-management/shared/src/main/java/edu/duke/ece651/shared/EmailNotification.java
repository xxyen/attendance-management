package edu.duke.ece651.shared;

public class EmailNotification implements Notification {
  private Email fromAddress;
  private Email toAddress;
  public EmailNotification(Email fromAddress, Email toAddress) {
    this.fromAddress = fromAddress;
    this.toAddress = toAddress;
  }
  public boolean sendNotification() {
  }
}