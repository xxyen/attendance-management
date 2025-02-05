package edu.duke.ece651.shared;

import java.io.IOException;
import java.util.Objects;

/**
 * This class represent student.
 * @author Can Pei
 * @version 1.0
 */
public class Student implements User {
  /**
   * Student ID
   */
  private String studentID;

  /**
   * Student's legal name
   */
  private String legalName;

  /**
   * Student's display name
   */
  private String displayName;

  /**
   * Student's email address
   */
  private Email emailAddr;

  /**
   * Constructor
   */
  public Student(String studentID, String legalName, String displayName, Email emailAddr) {
    this.studentID = studentID;
    this.legalName = legalName;
    this.displayName = displayName;
    this.emailAddr = emailAddr;
  }

  public void setDisplayName(String displayName) throws IOException {
    this.displayName = displayName;
  }

  public void setEmailAddr(Email emailAddr) throws IOException {
    this.emailAddr = emailAddr;
  }

  public String getPersonalID() {
    return studentID;
  }

  public String getLegalName() {
    return legalName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Email getEmailAddr() {
    return emailAddr;
  }

  @Override
  public String getUserType() {
    return "student";
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Student student = (Student) o;
    return Objects.equals(studentID, student.studentID) && Objects.equals(legalName, student.legalName)
        && Objects.equals(displayName, student.displayName) && Objects.equals(emailAddr, student.emailAddr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(studentID, legalName, displayName, emailAddr);
  }

}
