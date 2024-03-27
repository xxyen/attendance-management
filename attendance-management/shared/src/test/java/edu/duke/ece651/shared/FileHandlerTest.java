package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class FileHandlerTest {

  private static final String DATA_PATH = System.getProperty("user.dir") +
      "/testdata/";
  private static final String COURSE_ID = "testCourse";
  private static final String PROFESSOR_ID = "testProfessor";

  @BeforeEach
  public void setUp() throws Exception {
    FileHandler.setDataPath(DATA_PATH);
  }

  @Test
  void test_loadGlobalStudents() throws FileNotFoundException {
    Map<String, Student> students = FileHandler.loadGlobalStudents();
    // assertEquals(4, students.size());

    Student student1 = students.get("s001");
    assertNotNull(student1);
    assertEquals("John Doe", student1.getLegalName());
    assertEquals("John", student1.getDisplayName());
    assertEquals("s001@duke.edu", student1.getEmailAddr().getEmailAddr());
  }

  @Test
  void test_loadGlobalProfessors() {
    Map<String, Professor> professors = FileHandler.loadGlobalProfessors();
    // assertEquals(2, professors.size());

    Professor professor1 = professors.get("p001");
    assertNotNull(professor1);
    assertEquals("Professor X", professor1.getName());
    assertEquals("profx@gmail.com", professor1.getEmail().getEmailAddr());
  }

  @Test
  void test_loadCourses() throws ParseException, FileNotFoundException {
    Map<String, Student> globalStudents = FileHandler.loadGlobalStudents();
    Map<String, Professor> globalProfessors = FileHandler.loadGlobalProfessors();
    List<Course> courses = FileHandler.loadCourses(globalStudents,
        globalProfessors);

    assertFalse(courses.isEmpty());
    Course course123 = courses.stream().filter(c -> "course123".equals(c.getCourseid())).findFirst().orElse(null);
    assertNotNull(course123);

    assertEquals(2, course123.getStudents().size());
    assertEquals("s001", course123.getStudents().get(0).getPersonalID());

    assertEquals(1, course123.getProfessors().size());
    assertEquals("p002", course123.getProfessors().get(0).getPersonalID());
    assertEquals("Professor Y", course123.getProfessors().get(0).getName());
    assertTrue(course123.getProfessors().get(0).hasCourse("course123"));

    assertEquals(1, course123.getSessions().size());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
    Date expectedDate = sdf.parse("2024-03-21_22-00");
    Session testSession = course123.getSessions().get(0);
    assertEquals(expectedDate, testSession.getTime());
    assertEquals("course123", testSession.getCourseid());

    List<AttendanceRecord> attendanceRecords = testSession.getRecords();
    assertEquals(2, attendanceRecords.size());

    assertEquals("s001", attendanceRecords.get(0).getStudent().getPersonalID());
    assertEquals('p', attendanceRecords.get(0).getStatus().getStatus());

    assertEquals("s002", attendanceRecords.get(1).getStudent().getPersonalID());
    assertEquals('a', attendanceRecords.get(1).getStatus().getStatus());
  }

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
    // Course cour = courses.get(1);

    Course cour = null;
    for (Course course : courses) {
      if ("course456".equals(course.getCourseid())) {
        cour = course;
        break;
      }
    }
    // Course cour = new Course("course456", null, null, true);

    FileHandler.loadRosterFromCsv(cour.getCourseid(), cour, rosterPath);

    assertFalse(cour.getStudents().isEmpty());
    assertEquals(2, cour.getStudents().size());
    assertEquals("s003", cour.getStudents().get(0).getPersonalID());
    assertEquals("David", cour.getStudents().get(0).getDisplayName());
    assertEquals("s004", cour.getStudents().get(1).getPersonalID());
  }

  @Test
  public void test_createAndDeleteCourse() {
    FileHandler.createCourse(COURSE_ID, PROFESSOR_ID);

    File courseDir = new File(DATA_PATH + "/" + COURSE_ID);
    File sessionsDir = new File(courseDir, "/sessions");
    File professorFile = new File(courseDir, "/Professor_" + COURSE_ID +
        ".json");

    assertTrue(courseDir.exists());
    assertTrue(sessionsDir.exists());
    assertTrue(professorFile.exists());

    FileHandler.deleteCourse(COURSE_ID);
    assertFalse(courseDir.exists());
  }

  @Test
  public void test_addProfessorToCourse() {
    FileHandler.createCourse(COURSE_ID, PROFESSOR_ID);
    FileHandler.addProfessorToCourse(PROFESSOR_ID, COURSE_ID);

    File professorFile = new File(DATA_PATH + "/" + COURSE_ID + "/Professor_" +
        COURSE_ID + ".json");
    assertTrue(professorFile.exists());
  }

  @Test
  public void test_addSessionToCourse() {
    FileHandler.createCourse(COURSE_ID, PROFESSOR_ID);
    Date now = new Date();
    FileHandler.addSessionToCourse(COURSE_ID, now);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
    String sessionFileName = dateFormat.format(now) + ".txt";
    File sessionFile = new File(DATA_PATH + "/" + COURSE_ID + "/sessions/" +
        sessionFileName);
    assertTrue(sessionFile.exists());
  }

  @Test
  public void test_addStudentToCourse() throws Exception {
    Student testStudent = new Student("test1", "Legal Name", "Display ame",
        new Email("test@duke.edu"));

    FileHandler.createCourse(COURSE_ID, PROFESSOR_ID);

    FileHandler.addStudentToCourse(testStudent.getPersonalID(), COURSE_ID);

    String studentListPath = DATA_PATH + "/" + COURSE_ID + "/StudentList_" +
        COURSE_ID + ".json";
    File studentListFile = new File(studentListPath);
    assertTrue(studentListFile.exists());

    String content = new String(Files.readAllBytes(Paths.get(studentListPath)));
    JSONArray studentsArray = new JSONArray(content);
    boolean found = false;
    for (int i = 0; i < studentsArray.length(); i++) {
      if (studentsArray.getJSONObject(i).getString("studentID").equals(testStudent.getPersonalID())) {
        found = true;
        break;
      }
    }
    assertTrue(found);

    Student updatedStudent = new Student("test1", "Legal Name", "New Display ame",
        new Email("test@duke.edu"));

    FileHandler.updateCoursesForStudent(updatedStudent);
  }

}
