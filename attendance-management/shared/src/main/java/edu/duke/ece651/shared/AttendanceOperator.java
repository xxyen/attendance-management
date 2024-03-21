package edu.duke.ece651.shared;

public interface AttendanceOperator {
  public AttendanceRecord takeAttendance(Student student, Status status);
  public void changeSingleRecord(AttendanceRecord record, Status status);
}
