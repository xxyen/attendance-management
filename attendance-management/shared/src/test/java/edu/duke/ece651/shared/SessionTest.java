package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
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

  // This method has been tested. The file is encrypted, so you need to adjust the
  // file name and change saveAttendanceRecords() when testing this function
  @Disabled
  @Test
  public void test_saveAttendanceRecords() throws Exception {
    Session session = new Session("course101", new Date());
    Student student1 = new Student("id001", "Will Doe", "Will", new Email("will@gmail.com"));
    Student student2 = new Student("id002", "Grace Doe", "Grace", new Email("grace@gmail.com"));
    session.addRecord(new AttendanceRecord(student1, new Status('p')));
    session.addRecord(new AttendanceRecord(student2, new Status('a')));

    session.saveAttendanceRecords();

    String workingDir = System.getProperty("user.dir");
    String DATA_PATH = workingDir + "/data/" + session.getCourseid() + "/sessions/";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
    String fileName = dateFormat.format(session.getTime()) + ".txt.temp";
    String path = DATA_PATH + fileName;

    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line1 = reader.readLine();
      String line2 = reader.readLine();

      assertEquals("id001, p", line1);
      assertEquals("id002, a", line2);
    }
  }
}
