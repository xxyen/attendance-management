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

  /**
   * Constructor
   */
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


  public void setDisplayName(String displayName) throws IOException {
    this.displayName = displayName;
  }

  public void setEmailAddr(Email email) throws IOException {
    this.email = email;
  }

  @Override
  public String getUserid() {
    return userid;
  }

  public String getLegalName() {
    return legalName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Email getEmailAddr() {
    return email;
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
    return Objects.equals(userid, student.userid) && Objects.equals(legalName, student.legalName)
        && Objects.equals(displayName, student.displayName) && Objects.equals(email, student.email);
  }

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

  @Override
  public boolean saveToDB() {
    try {
      String sql = "INSERT INTO student (user_id, password_hash, email, legal_name, display_name) VALUES (?, ?, ?, ?, ?)";
      PreparedStatement statement = JDBCUtils.getConnection().prepareStatement(sql);
      statement.setString(1, userid);
      statement.setString(2, password);
      statement.setString(3, email.getEmailAddr());
      statement.setString(4, legalName);
      statement.setString(5, displayName);
      
      int rowsInserted = statement.executeUpdate();
      if (rowsInserted > 0) {
        return true;
      }
    } catch (SQLException e) {
      System.out.println("Error inserting student: " + e.getMessage());
    } finally {
      //JDBCUtils.close(resultSet, statement, connection);
      return false;
    }
  }

}
