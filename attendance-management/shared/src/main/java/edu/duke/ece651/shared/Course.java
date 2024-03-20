package edu.duke.ece651.shared;

import java.util.ArrayList;

public class Course {
  private final ArrayList<Student> students;
  private final ArrayList<Session> sessions;
  private boolean canChangeName;

  public Course(boolean canChangeName) {
    students = new ArrayList<>();
    sessions = new ArrayList<>();
    this.canChangeName = canChangeName;
  }

}
