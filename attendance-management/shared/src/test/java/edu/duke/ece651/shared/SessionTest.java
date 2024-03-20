package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SessionTest {
  @Test
  public void test_session() {
    Date cur = new Date();
    Session session = new Session("651", cur);
    assertEquals("651", session.getCourseid());
    assertTrue(session.getRecords().isEmpty());

    Student stud1 = new Student("zx123", "Zoe X", "Zoe", new Email("zoe.x@duke.edu"));
    Status status1 = new Status('p');
    AttendanceRecord record1 = new AttendanceRecord(stud1, status1);
    session.addRecord(record1);

    assertEquals(1, session.getRecords().size());

    Student stud2 = new Student("cg456", "Carol G", "Carol", new Email("carol.g@duke.edu"));
    Status status2 = new Status('a');
    AttendanceRecord record2 = new AttendanceRecord(stud2, status2);
    session.addRecord(record2);

    List<AttendanceRecord> records = session.getRecords();
    assertEquals(2, records.size());
    assertEquals("zx123", records.get(0).getStudent().getStudentID());
    assertEquals('a', records.get(1).getStatus().getStatus());
    assertEquals(cur, session.getTime());
  }

}
