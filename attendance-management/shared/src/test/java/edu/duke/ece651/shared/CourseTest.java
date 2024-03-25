package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CourseTest {
  private Course course;
  private Student stud1, stud2;
  private Professor prof1;
  private Session session1;

  public void createCourse() {
    stud1 = new Student("zx123", "Zoe X", "Zoey", new Email("zoe.x@duke.edu"));
    stud2 = new Student("cg456", "Carol G", "Caroline", new Email("carol.g@duke.edu"));
    Student[] studentsArray = { stud1 };
    prof1 = new Professor("John", "jh123", new Email("jh123@duke.edu"));
    Professor[] professorsArray = { prof1 };

    course = new Course("ECE651", professorsArray, studentsArray, true);
    // course = new Course("ECE651", studentsArray, true);

    session1 = new Session("ECE651", new Date());
  }

  @Test
  public void test_constructor() {
    Course course = new Course(false);
    assertFalse(course.isCanChangeName());
  }

  @Test
  public void test_properties() {
    createCourse();
    assertEquals("ECE651", course.getCourseid());
    assertTrue(course.isCanChangeName());
  }

  @Test
  public void test_addStudent() throws IOException {
    createCourse();
    assertEquals(1, course.getStudents().size());
    course.addStudent(stud2);
    assertEquals(2, course.getStudents().size());
  }

  @Test
  public void test_removeStudent() throws IOException {
    createCourse();
    course.addStudent(stud2);
    assertEquals(2, course.getStudents().size());
    course.removeStudent(stud1.getPersonalID());
    assertEquals(1, course.getStudents().size());
  }

  @Test
  public void test_addSession() {
    createCourse();
    course.addSession(session1);
    assertEquals(1, course.getSessions().size());
  }

  @Test
  public void test_getStudent() {
    createCourse();
    Iterator<Student> studentIterator = course.getStudent();
    assertTrue(studentIterator.hasNext());
    assertEquals(stud1, studentIterator.next());
    assertFalse(studentIterator.hasNext());
  }

  @Test
  public void test_getStudents() throws IOException {
    createCourse();
    course.addStudent(stud2);
    List<Student> students = course.getStudents();
    assertEquals(2, students.size());
    assertTrue(containsStudent(students, stud1));
    assertTrue(containsStudent(students, stud2));
  }

  private boolean containsStudent(List<Student> students, Student expectedStudent) {
    for (Student stu : students) {
      if (stu.getPersonalID().equals(expectedStudent.getPersonalID())) {
        return true;
      }
    }
    return false;
  }
}
