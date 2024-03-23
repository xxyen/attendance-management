package edu.duke.ece651.shared;

import java.util.ArrayList;

public class Professor implements User {
  private String name;
  private String professorID;
  private Email email;
  private ArrayList<String> courseids;
  
  public Professor(String name, String professorID, Email email, ArrayList<String> courseids) {
    this.name = name;
    this.professorID = professorID;
    this.email = email;
    this.courseids = courseids;
  }
  
  public Professor(String name, String professorID, Email email) {
    this(name, professorID, email, new ArrayList<>());
  }
  
  public String getName() {
    return name;
  }

  @Override
  public String getPersonalid() {
    return professorID;
  }
  
  public Email getEmail() {
    return email;
  }
  
  public boolean hasCourse(String courseid){
    return courseids.contains(courseid);
  }
  public void addCourse(String courseid) {
    if (courseids.contains(courseid)) {
      throw new IllegalArgumentException(courseid + " exists!");
    }
    courseids.add(courseid);
  }
  
  public void removeCourse(String courseid) {
    if (courseids.contains(courseid)) {
      courseids.remove(courseid);
      return;
    }
    throw new IllegalArgumentException(name + " doesn't teach " + courseid + "!");      
  }
  
}
