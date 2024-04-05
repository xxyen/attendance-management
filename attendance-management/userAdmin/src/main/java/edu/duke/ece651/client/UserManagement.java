package edu.duke.ece651.client;

import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.dao.FacultyDAO;
import edu.duke.ece651.shared.dao.StudentDAO;

public class UserManagement {
    private static FacultyDAO facultyDAO = new FacultyDAO();
    private static StudentDAO studentDAO = new StudentDAO();

    public static boolean facultySignUp(Professor faculty) {
    try {
      facultyDAO.addFaculty(faculty);
      return true;
    } catch (Exception e) { // duplicat
      System.out.println("Failed to sign up (faculty): " + e.getMessage());
      return false;
    }
  }

  public static boolean studentSignUp(Student student) {
    try {
      studentDAO.addStudent(student);
      return true;
    } catch (Exception e) { // duplicate
      System.out.println("Failed to sign up (student): " + e.getMessage());
      return false;
    }
  }

  public static void removeFaculty(String userid) {
    facultyDAO.deleteFaculty(userid);
  }

  public static void removeStudent(String userid) {
    studentDAO.deleteStudent(userid);
  }

  public static void updateFaculty(Professor faculty) {
    facultyDAO.updateFaculty(faculty);
  }

  public static void updateStudent(Student student) {
    studentDAO.updateStudent(student);
  }

  public static Student getStudentByID(String userid) {
    return studentDAO.queryStudentById(userid);
  }
  public static Professor getFacultyByID(String userid) {
    return facultyDAO.queryFacultyById(userid);
  }
}
