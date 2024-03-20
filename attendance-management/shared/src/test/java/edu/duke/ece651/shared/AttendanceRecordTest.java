package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttendanceRecordTest {
  @Test
  public void test_AttendanceRecord() {
    Student stud = new Student("zx123", "Zoe X", "Zoe", new Email("zoe.x@duke.edu"));
    Status status = new Status('p');
    AttendanceRecord record = new AttendanceRecord(stud, status);

    assertEquals("zx123", record.getStudent().getStudentID());
    assertEquals('p', record.getStatus().getStatus());
  }

}