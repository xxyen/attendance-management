package edu.duke.ece651.shared;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a course with its details such as course ID, professors, students,
 * and sessions.
 * Allows manipulation of these details including adding and removing students
 * and professors.
 */
public class Course {
  private String courseId;
  private String courseName;

  public Course(){
  }

  public Course(String courseId, String courseName){
    this.courseId = courseId;
    this.courseName = courseName;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
      this.courseId = courseId;
  }

  public String getCourseName() {
      return courseName;
  }

  public void setCourseName(String courseName) {
      this.courseName = courseName;
  }
}
