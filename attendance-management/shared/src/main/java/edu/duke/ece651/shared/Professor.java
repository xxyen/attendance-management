package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.io.IOException;

/**
 * This class represents the professor.
 * Some basic getters are provided.
 * Course List of the professor can be handled.
 */
public class Professor implements User {
  private String userid;
  private String password;
  private String name;
  private Email email;

  /**
   * Construct a professor with name, id, email and their course list
   */
  public Professor(String userid, String password, String name, Email email) {
    this.name = name;
    this.userid = userid;
    this.password = password;
    this.email = email;
  }

  /**
   * Construct a professor  with name, id and email.
   * The default password is set to be the same as their userid.
   */
  public Professor(String userid, String name, Email email) {
    this(userid, userid, name, email);
  }

  /**
   * get user type, always return "professor" for this class
   */
  @Override
  public String getUserType() {
    return "professor";
  }


  /**
   * get professor name
   */
  public String getName() {
    return name;
  }

  /**
   * get user id
   */
  @Override
  public String getUserid() {
    return userid;
  }

  /**
   * get email string
   */
  @Override
  public Email getEmail() {
    return email;
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
   * set the password of the faculty member
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
   * get password of the faculty member
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * check if the password is the default one (same as their userid)
   */
  @Override
  public boolean isDefaultPwd() {
    return userid.equals(password);
  }
}
