package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class FileHandlerTest {
  @Test
  void test_loadGlobalStudents() throws FileNotFoundException {
    Map<String, Student> students = FileHandler.loadGlobalStudents();
    assertEquals(4, students.size());

    Student student1 = students.get("s001");
    assertNotNull(student1);
    assertEquals("John Doe", student1.getLegalName());
    assertEquals("John", student1.getDisplayName());
    assertEquals("s001@duke.edu", student1.getEmailAddr().getEmailAddr());
  }

  @Test
  void test_loadGlobalProfessors() {
    Map<String, Professor> professors = FileHandler.loadGlobalProfessors();
    assertEquals(2, professors.size());

    Professor professor1 = professors.get("p001");
    assertNotNull(professor1);
    assertEquals("Professor X", professor1.getName());
    assertEquals("profx@gmail.com", professor1.getEmail().getEmailAddr());
  }

  @Test
  void test_loadCourses() throws ParseException, FileNotFoundException {
    Map<String, Student> globalStudents = FileHandler.loadGlobalStudents();
    Map<String, Professor> globalProfessors = FileHandler.loadGlobalProfessors();
    List<Course> courses = FileHandler.loadCourses(globalStudents, globalProfessors);

    assertFalse(courses.isEmpty());
    Course course123 = courses.stream().filter(c -> "course123".equals(c.getCourseid())).findFirst().orElse(null);
    assertNotNull(course123);

    assertEquals(2, course123.getStudents().size());
    assertEquals("s001", course123.getStudents().get(0).getStudentID());

    assertEquals(1, course123.getProfessors().size());
    assertEquals("p002", course123.getProfessors().get(0).getPersonalid());
    assertEquals("Professor Y", course123.getProfessors().get(0).getName());

    assertEquals(1, course123.getSessions().size());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
    Date expectedDate = sdf.parse("2024-03-21_22-00");
    Session testSession = course123.getSessions().get(0);
    assertEquals(expectedDate, testSession.getTime());
    assertEquals("course123", testSession.getCourseid());

    List<AttendanceRecord> attendanceRecords = testSession.getRecords();
    assertEquals(2, attendanceRecords.size());

    assertEquals("s001", attendanceRecords.get(0).getStudent().getStudentID());
    assertEquals('p', attendanceRecords.get(0).getStatus().getStatus());

    assertEquals("s002", attendanceRecords.get(1).getStudent().getStudentID());
    assertEquals('a', attendanceRecords.get(1).getStatus().getStatus());
  }

  // It has been tested
  @Disabled
  @Test
  void test_loadRosterFromCsv() throws IOException {
    String workingDir = System.getProperty("user.dir");
    String path = workingDir + "/roster/";
    String rosterPath = path + "roster_course456.csv";

    Map<String, Student> globalStudents = FileHandler.loadGlobalStudents();
    Map<String, Professor> globalProfessors = FileHandler.loadGlobalProfessors();
    List<Course> courses = FileHandler.loadCourses(globalStudents,
        globalProfessors);
    Course cour = courses.get(1);
    // Course cour = new Course("course456", null, null, true);

    FileHandler.loadRosterFromCsv(cour.getCourseid(), cour, rosterPath);

    assertFalse(cour.getStudents().isEmpty());
    assertEquals(2, cour.getStudents().size());
    assertEquals("s003", cour.getStudents().get(0).getStudentID());
    assertEquals("David", cour.getStudents().get(0).getDisplayName());
    assertEquals("s004", cour.getStudents().get(1).getStudentID());
  }

}
