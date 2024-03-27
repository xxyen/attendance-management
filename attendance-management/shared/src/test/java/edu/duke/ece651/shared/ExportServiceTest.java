package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ExportServiceTest {
  private List<Session> createSessions() throws ParseException {
    List<Session> sessions = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fixedDateTime = "2024-03-26 19:00:00";

    Date fixedDate = dateFormat.parse(fixedDateTime);
    Session session1 = new Session("course01", fixedDate);

    Student student1 = new Student("s01", "Legal Name01", "name01", new Email("email1@qq.com"));
    AttendanceRecord record1 = new AttendanceRecord(student1, new Status('p'));
    session1.addRecord(record1);

    Student student2 = new Student("s02", "Legal Name02", "name02", new Email("email2@gmail.com"));
    AttendanceRecord record2 = new AttendanceRecord(student2, new Status('a'));
    session1.addRecord(record2);

    sessions.add(session1);

    Session session2 = new Session("course02", new Date());
    Student student3 = new Student("s03", "Legal Name03", "name03", new Email("email3@duke.edu"));
    AttendanceRecord record3 = new AttendanceRecord(student3, new Status('l'));
    session2.addRecord(record3);

    sessions.add(session2);

    return sessions;
  }

  @Test
  public void testExportToJson() throws IOException, ParseException {
    List<Session> sessions = createSessions();
    String workingDir = System.getProperty("user.dir");
    String filePath1 = workingDir + "/export/" + "test_record1.json";

    ExportService.exportToFile(sessions, "json",
        Arrays.asList("studentID", "legalName", "displayName", "email", "status"), filePath1);

    File file1 = new File(filePath1);
    assertTrue(file1.exists());

    String filePath2 = workingDir + "/export/" + "test_record2.json";

    ExportService.exportToFile(sessions, "json",
        Arrays.asList("studentID", "legalName", "status"), filePath2);

    File file2 = new File(filePath2);
    assertTrue(file2.exists());

  }

  @Test
  public void testExportToXml() throws IOException, ParseException {
    List<Session> sessions = createSessions();
    String workingDir = System.getProperty("user.dir");
    String filePath = workingDir + "/export/" + "test_record1.xml";

    ExportService.exportToFile(sessions, "xml",
        Arrays.asList("studentID", "legalName", "displayName", "email", "status"), filePath);

    File file = new File(filePath);
    assertTrue(file.exists());
  }

  @Test
  public void testExportToCustomOrInvalid() throws IOException, ParseException {
    List<Session> sessions = createSessions();
    String workingDir = System.getProperty("user.dir");
    String filePath = workingDir + "/export/" + "test_record1.custom";

    ExportService.exportToFile(sessions, "custom",
        Arrays.asList("studentID", "legalName", "displayName", "email", "status"), filePath);

    assertThrows(IllegalArgumentException.class, () -> ExportService.exportToFile(sessions, "hhh",
        Arrays.asList("studentID", "legalName", "displayName", "email", "status"), filePath));
  }

}
