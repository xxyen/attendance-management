package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BasicAttendanceOperatorTest {
  @Test
  public void test_testAttendance() {
    Email e1 = new Email("abc@gmail.com");
    Student s1 = new Student("cp357", "Can Pei", "Alex Pei", e1);
    Status status = new Status('p');
    AttendanceRecord expected = new AttendanceRecord(s1, status);
    AttendanceOperator basicOp = new BasicAttendanceOperator();
    AttendanceRecord returned = basicOp.takeAttendance(s1, status);
    assertEquals(expected.getStudent().getPersonalID(), returned.getStudent().getPersonalID());
    assertEquals(expected.getStatus().getStatus(), returned.getStatus().getStatus());
  }

  @Test
  public void test_changeSingleRecord() {
    Date cur = new Date();
    Session session = new Session("651", cur);
    Email e1 = new Email("abc@gmail.com");
    Student s1 = new Student("cp357", "Can Pei", "Alex Pei", e1);
    Student s2 = new Student("zx123", "Zoe X", "Zoe", new Email("zoe.x@duke.edu"));
    Status status1 = new Status('p');
    Status status2 = new Status('a');
    BasicAttendanceOperator basicOp = new BasicAttendanceOperator();
    session.addRecord(basicOp.takeAttendance(s1, status1));
    session.addRecord(basicOp.takeAttendance(s2, status1));
    basicOp.changeSingleRecord(session, s2, status2);
    List<AttendanceRecord> records = session.getRecords();
    assertEquals(2, records.size());
    assertEquals("zx123", records.get(1).getStudent().getPersonalID());
    assertEquals('p', records.get(0).getStatus().getStatus());
    assertEquals('a', records.get(1).getStatus().getStatus());
    assertEquals(cur, session.getTime());
  }

}
