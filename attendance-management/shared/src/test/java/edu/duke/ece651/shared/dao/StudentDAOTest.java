package edu.duke.ece651.shared.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.shared.Email;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.dao.StudentDAO;

public class StudentDAOTest {
  private Student stu1 = new Student("user123", "hash123", "John Doe", "Johnny", new Email("john.doe@example.com"));
  private Student stu2 = new Student("user456", "hash456", "Jane Doe", "Janie", new Email("jane.doe@example.com"));
  private Student stu3 = new Student("user101", "hash101", "Sam Johnson", "Sammy",
      new Email("sam.johnson@example.com"));

  @Test
  public void test_addStudent() throws Exception {
    StudentDAO studentDAO = new StudentDAO();

    int result = studentDAO.addStudent(stu1);
    assertEquals(1, result);
    Student queriedStudent = studentDAO.queryStudentById("user123");
    System.out.println(queriedStudent);

    assertNotNull(queriedStudent);
    assertEquals("John Doe", queriedStudent.getLegalName());
    result = studentDAO.deleteStudent("user123");

  }

  @Test
  public void test_deleteStudent() throws Exception {
    StudentDAO studentDAO = new StudentDAO();
    studentDAO.addStudent(stu2);
    int result = studentDAO.deleteStudent("user456");
    assertEquals(1, result);
    Student queriedStudent = studentDAO.queryStudentById("user456");
    System.out.println(queriedStudent);
    assertNull(queriedStudent);
  }

  @Test
  public void test_updateStudent() throws Exception {
    StudentDAO studentDAO = new StudentDAO();
    Student student = new Student("user789", "hash789", "Alex Smith", "Alex", new Email("alex.smith@example.com"));
    studentDAO.addStudent(student);

    student.setDisplayName("Alexander");
    int result = studentDAO.updateStudent(student);
    assertEquals(1, result);
    Student queriedStudent = studentDAO.queryStudentById("user789");
    System.out.println(queriedStudent);

    assertNotNull(queriedStudent);
    assertEquals("Alexander", queriedStudent.getDisplayName());
    result = studentDAO.deleteStudent("user789");

  }

  @Test
  public void test_queryStudentById() throws Exception {
    StudentDAO studentDAO = new StudentDAO();

    Student student = new Student("user101", "hash101", "Sam Johnson", "Sammy", new Email("sam.johnson@example.com"));
    studentDAO.addStudent(student);

    Student queriedStudent = studentDAO.queryStudentById("user101");
    System.out.println(queriedStudent);
    assertNotNull(queriedStudent);
    assertEquals("Sam Johnson", queriedStudent.getLegalName());
    assertEquals("user101", queriedStudent.getUserid());
    assertEquals("Sammy", queriedStudent.getDisplayName());
    //assertEquals("sam.johnson@example.com", queriedStudent.getEmail().getEmailAddr());
    int result = studentDAO.deleteStudent("user101");
  }

  @Test
  public void test_queryAllStudents() {
    StudentDAO studentDAO = new StudentDAO();
    studentDAO.addStudent(stu1);
    studentDAO.addStudent(stu2);
    studentDAO.addStudent(stu3);
    List<Student> students = studentDAO.queryAllStudents();
    // assertEquals(students, stu1);
    int result = studentDAO.deleteStudent("user123");
    result = studentDAO.deleteStudent("user456");
    result = studentDAO.deleteStudent("user101");
  }
}
