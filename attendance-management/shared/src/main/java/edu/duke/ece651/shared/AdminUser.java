package edu.duke.ece651.shared;

import java.sql.SQLException;

import edu.duke.ece651.shared.dao.FacultyDAO;
import edu.duke.ece651.shared.dao.StudentDAO;

public class AdminUser implements User {
  private static final AdminUser INSTANCE = new AdminUser();
  private String userid = "admin";
  private String password = "SthYouDonotKnow77890";
  private FacultyDAO facultyDAO = new FacultyDAO();
  private StudentDAO studentDAO = new StudentDAO();

  private AdminUser() {
  }

  public static AdminUser getInstance() {
    return INSTANCE;
  }

  @Override
  public String getUserid() {
    return userid;
  }

  @Override
  public String getUserType() {
    return "administrator";
  }

  @Override
  public boolean isCorrectPassword(String pwd) {
    return pwd.equals(password);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Email getEmail() {
    return null;
  }

  public boolean facultySignUp(Professor faculty) {
    try {
      facultyDAO.addFaculty(faculty);
      return true;
    } catch (Exception e) { // duplicat
      System.out.println("Failed to sign up (faculty): " + e.getMessage());
      return false;
    }
  }

  public boolean studentSignUp(Student student) {
    try {
      studentDAO.addStudent(student);
      return true;
    } catch (Exception e) { // duplicate
      System.out.println("Failed to sign up (student): " + e.getMessage());
      return false;
    }
  }

  public void removeFaculty(String userid) {
    facultyDAO.deleteFaculty(userid);
  }

  public void removeStudent(String userid) {
    studentDAO.deleteStudent(userid);
  }

  public void updateFaculty(Professor faculty) {
    facultyDAO.updateFaculty(faculty);
  }

  public void updateStudent(Student student) {
    studentDAO.updateStudent(student);
  }

  /**
   * set the password of the admin
   * not allowed to be modified for now
   */
  @Override
  public boolean setPwd(String newPwd) {
    return false;
  }

  @Override
  public boolean isDefaultPwd() {
    return userid.equals(password);
  }

  public Student getStudentByID(String userid) {
    return studentDAO.queryStudentById(userid);
  }
  public Professor getFacultyByID(String userid) {
    return facultyDAO.queryFacultyById(userid);
  }
}
