package edu.duke.ece651.shared.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.shared.Email;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.dao.StudentDAO;
import org.junit.jupiter.api.Disabled;


public class StudentDAOTest {
  StudentDAO studentDAO = new StudentDAO();

  @Test
  public void test_All() throws Exception {
    String userid = "notgoingtohapendhiudhushf";
    Student student = new Student(userid, userid, userid, new Email("1ac@1ac.shd"));
    studentDAO.queryStudentById(userid);
    studentDAO.queryStudentByEmail("1ac@1ac.shd");
    studentDAO.queryStudentByLegalName(userid); 
    studentDAO.addStudent(student);
    studentDAO.queryStudentById(userid);
    studentDAO.queryStudentByEmail("1ac@1ac.shd");
    studentDAO.queryStudentByLegalName(userid);
    studentDAO.updateStudent(student);
    studentDAO.deleteStudent(userid);
    studentDAO.queryAllStudents();
  }
}
