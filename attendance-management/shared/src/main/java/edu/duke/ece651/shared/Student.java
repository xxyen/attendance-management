package edu.duke.ece651.shared;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
  private String userid;

  private String password;

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
  private Email email;

  public Student() {
  }

  /**
   * Constructor
   */
  // public Student(String userid, String password, String legalName, String displayName, Email email) {
  //   this.userId = userid;
  //   this.passwordHash = password;
  //   this.legalName = legalName;
  //   this.displayName = displayName;
  //   this.email = email;
  // }
  public Student(String userid, String password, String legalName, String displayName, Email email) {
    this.userid = userid;
    this.password = password;
    this.legalName = legalName;
    this.displayName = displayName;
    this.email = email;
  }

   /**
   * Constructor (default password, which is the same as userid)
   */
  public Student(String userid, String legalName, String displayName, Email email) {
    this(userid, userid, legalName, displayName, email);
  }

  /**
   * Constructor (email string)
   */
  // public Student(String userid, String password, String legalName, String displayName, String email) {
  //   this(userid, password, legalName, displayName, new Email(email));
  // }


  /**
   * set the display name of the user
   * @param displayName
   */
  public void setDisplayName(String displayName){
    this.displayName = displayName;
  }

  /**
   * set email address of the user
   * @param email (object)
   */
  public void setEmailAddr(Email email){
    this.email = email;
  }

  /**
   * set email address of the user
   * @param email (String)
   * @throws IOException if the email address is invalid
   */
  public void setEmail(String email) throws IOException {
    this.email = new Email(email);
  }

  /**
   * set userid of the student
   * @param userid
   */
  public void setUserid(String userid) {
    this.userid = userid;
  }

  /**
   * get userid of the student
   */
  @Override
  public String getUserid() {
    return userid;
  }

  /**
   * get legal name of the student
   * @return legal name of the student
   */
  public String getLegalName() {
    return legalName;
  }

  /**
   * get display name of the student
   * @return display name of the student
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * get user type, always return "student" for this class
   */
  @Override
  public String getUserType() {
    return "student";
  }

  /**
   * equals
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Student student = (Student) o;
    return Objects.equals(userid, student.userid) && Objects.equals(legalName, student.legalName)
        && Objects.equals(displayName, student.displayName) && Objects.equals(email, student.email);
  }

  /**
   * hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(userid, legalName, displayName, email);
  }

  /**
   * check if the password is correct.
   */
  @Override
  public boolean isCorrectPassword(String pwd) {
    if(pwd == null) {
      return false;
    }
    if(pwd.equals(password)) {
      return true;
    }
    return false;
  }

  /**
   * get password of the student
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * get email string
   */
  @Override
  public Email getEmail() {
    return email;
  }

  /**
   * set the password of the user
   * setting fails and returns false if the newPwd is empty
   * or contains any space
   */
  @Override
  public boolean setPwd(String newPwd) {
    if(newPwd.equals("") || newPwd.contains(" ")) {
      return false;
    }
    this.password = newPwd;
    return true;
  }


  /**
   * check if the password is the default one
   */
  @Override
  public boolean isDefaultPwd() {
    return userid.equals(password);
  }

  /**
   * to string
   */
  @Override
public String toString() {
    return "Student{" +
            "userid='" + userid + '\'' +
            ", password='" + password + '\'' +
            ", legalName='" + legalName + '\'' +
            ", displayName='" + displayName + '\'' +
            ", email=" + email +
            '}';
}
}
