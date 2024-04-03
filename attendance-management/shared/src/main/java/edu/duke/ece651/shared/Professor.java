package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

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
  public Professor(String name, String userid, Email email) {
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
   * get email object
   */
  public Email getEmail() {
    return email;
  }

  /**
   * set the password of the user
   * setting fails and returns false if the newPwd is empty
   * or contains any space
   */
  public boolean setPwd(String newPwd) {
    if(newPwd.equals("") || newPwd.contains(" ")) {
      return false;
    }
    this.password = newPwd;
    return true;
  }

  /**
   * check if a course is in the professor's course list
   */
  public boolean hasCourse(String courseid) {
    return false;
    // ??? access DB
  }

  /**
   * add a course to the professor's course list
   * 
   * @throws IllegalArgumentException if this course already exists in the course
   *                                  list
   */
  public void addCourse(String courseid) {
    // ??? access DB
    // find this course in db, if found and the falculty already has this course, throw
    // if not found, create it
    // if found and not "has", add it 

    // if (courseids.contains(courseid)) {
    //   throw new IllegalArgumentException(courseid + " exists!");
    // }
    // courseids.add(courseid);
  }

  /**
   * remove a course from the professor's course list
   * 
   * @throws IllegalArgumentException if this course is not in the course list
   */
  public void removeCourse(String courseid) {
    // ??? access DB
    // find this course in db, if found and the falculty already has this course, remove it
    // if not found, or found but not "has", throw
    
    // if (courseids.contains(courseid)) {
    //   courseids.remove(courseid);
    //   return;
    // }
    throw new IllegalArgumentException(name + " doesn't teach " + courseid + "!");
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
      String sql = "INSERT INTO faculty (user_id, password_hash, email, faculty_name) VALUES (?, ?, ?, ?)";
      PreparedStatement statement = JDBCUtils.getConnection().prepareStatement(sql);
      statement.setString(1, userid);
      statement.setString(2, password);
      statement.setString(3, email.getEmailAddr());
      statement.setString(4, name);
      
      int rowsInserted = statement.executeUpdate();
      if (rowsInserted > 0) {
        return true;
      }
    } catch (SQLException e) {
      System.out.println("Error inserting faculty: " + e.getMessage());
    } finally {
      //JDBCUtils.close(resultSet, statement, connection);
      return false;
    }
  }

}
