package edu.duke.ece651.shared;

import java.util.ArrayList;

/**
 * This class represents the professor.
 * Some basic getters are provided.
 * Course List of the professor can be handled.
 */
public class Professor implements User {
  private String name;
  private String professorID;
  private Email email;
  private ArrayList<String> courseids;

  /**
   * Construct a professor with name, id, email and their course list
   */
  public Professor(String name, String professorID, Email email, ArrayList<String> courseids) {
    this.name = name;
    this.professorID = professorID;
    this.email = email;
    this.courseids = courseids;
  }

  /**
   * get user type, always return "professor" for this class
   */
  @Override
  public String getUserType() {
    return "professor";
  }

  /**
   * Construct a professor with name, id and email
   */
  public Professor(String name, String professorID, Email email) {
    this(name, professorID, email, new ArrayList<>());
  }

  /**
   * get professor name
   */
  public String getName() {
    return name;
  }

  /**
   * get personal id
   */
  @Override
  public String getPersonalID() {
    return professorID;
  }

  /**
   * get email object
   */
  public Email getEmail() {
    return email;
  }

  /**
   * check if a course is in the professor's course list
   */
  public boolean hasCourse(String courseid){
    return courseids.contains(courseid);
  }

  /**
   * add a course to the professor's course list 
   * @throws IllegalArgumentException if this course already exists in the course list
   */
  public void addCourse(String courseid) {
    if (courseids.contains(courseid)) {
      throw new IllegalArgumentException(courseid + " exists!");
    }
    courseids.add(courseid);
  }

  /**
   * remove a course from the professor's course list 
   * @throws IllegalArgumentException if this course is not in the course list 
   */
  public void removeCourse(String courseid) {
    if (courseids.contains(courseid)) {
      courseids.remove(courseid);
      return;
    }
    throw new IllegalArgumentException(name + " doesn't teach " + courseid + "!");      
  }
  
}
