package edu.duke.ece651.shared;

public interface AttendanceOperator {
  public AttendanceRecord takeAttendance(Student student, Status status);
  // Return true if successfully changed record, otherwise return false
  public boolean changeSingleRecord(Session session, Student student, Status newStatus);
}
