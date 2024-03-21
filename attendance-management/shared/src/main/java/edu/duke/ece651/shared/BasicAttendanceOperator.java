package edu.duke.ece651.shared;

public class BasicAttendanceOperator implements AttendanceOperator {
  
  @Override
  public AttendanceRecord takeAttendance(Student student, Status status) {
    return new AttendanceRecord(student, status);
  }
  
  @Override
  public void changeSingleRecord(AttendanceRecord record, Status status) {
  }
}
