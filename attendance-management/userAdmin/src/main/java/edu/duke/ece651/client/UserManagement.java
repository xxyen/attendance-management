package edu.duke.ece651.userAdmin;

import java.util.List;

import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.dao.FacultyDAO;
import edu.duke.ece651.shared.dao.GlobalSettingDAO;
import edu.duke.ece651.shared.dao.StudentDAO;

public class UserManagement {
    private static FacultyDAO facultyDAO = new FacultyDAO();
    private static StudentDAO studentDAO = new StudentDAO();
    private static GlobalSettingDAO gsDAO = new GlobalSettingDAO();

    public static void facultySignUp(Professor faculty) throws Exception {
      facultyDAO.addFaculty(faculty);
  }

  public static void studentSignUp(Student student){
      studentDAO.addStudent(student);
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

  public static List<Student> getStudentByLegalName(String legalName) {
    return studentDAO.queryStudentByLegalName(legalName);
  }

  public static Professor getFacultyByID(String userid) {
    return facultyDAO.queryFacultyById(userid);
  }

  public static List<Professor> getFacultyByLegalName(String legalName) {
    return facultyDAO.queryFacultyByLegalName(legalName);
  }


  public static boolean checkFacultyExistsByID(String userid) {
    if(facultyDAO.queryFacultyById(userid) == null) {
        return false;
    }
    return true;
}

public static boolean checkFacultyExistsByEmail(String email) {
  if(facultyDAO.queryFacultyByEmail(email) == null) {
      return false;
  }
  return true;
}

public static boolean checkFacultyExistsByLegalName(String legalName) {
  if(facultyDAO.queryFacultyByLegalName(legalName) == null) {
      return false;
  }
  return true;
}

  public static boolean checkStudentExistsByID(String userid) {
    if(studentDAO.queryStudentById(userid) == null) {
        return false;
    }
    return true;
  }

  public static boolean checkStudentExistsByEmail(String email) {
    if(studentDAO.queryStudentByEmail(email) == null) {
        return false;
    }
    return true;
  }

  public static boolean checkStudentExistsByLegalName(String legalName) {
    if(studentDAO.queryStudentByLegalName(legalName).size() == 0) {
        return false;
    }
    return true;
  }

  public static int getDisplayNamePermissionModificationCount() {
    return gsDAO.queryModificationCount("allow_modify_display_name");
  }

  public static void setDisplayNamePermission(boolean permission) {
    gsDAO.updateDisplayNamePermission(permission);
  }

  public static boolean getDisplayNamePermission() {
    return gsDAO.queryDisplayNamePermission();
  }

}
