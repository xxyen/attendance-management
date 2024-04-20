package edu.duke.ece651.shared;
import edu.duke.ece651.shared.model.*;


public interface AttendanceOperator {
  /**
   * Create a single attendance record given student and status.
   * @param student is the student to record
   * @param status is the status to record
   * @return a single attendace record
   */
  public AttendanceRecord takeAttendance(Student student, Status status);
  // Return true if successfully changed record, otherwise return false
  /**
   * Change a single record given which session, student, and what's the new status
   * @param session is the chosen session
   * @param student is which student to change status
   * @param newStatus is the target status
   * @return true if successfully changed status, otherwise return false
   */
  public boolean changeSingleRecord(Session session, Student student, Status newStatus);
}
