package edu.duke.ece651.shared;

import java.util.Date;

public class AttendanceOperator {
  private final Course course;

  public AttendanceOperator(Course course) {
    this.course = course;
  }
  
  public AttendanceRecord takeAttendance(Student student, Status status) {
    return new AttendanceRecord(student, status);
  }

  //public AttendanceRecord changeSingleRecord(Student student) {
  //}
}
