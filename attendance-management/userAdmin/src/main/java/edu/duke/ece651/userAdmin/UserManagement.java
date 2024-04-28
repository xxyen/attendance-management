package edu.duke.ece651.userAdmin;

import java.util.List;
import java.util.Set;

import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.dao.FacultyDAO;
import edu.duke.ece651.shared.dao.GlobalSettingDAO;
import edu.duke.ece651.shared.dao.StudentDAO;

/**
 * The UserManagement class provides methods for managing user accounts and permissions.
 * It interacts with the FacultyDAO, StudentDAO, and GlobalSettingDAO classes to perform user-related operations.
 */
public class UserManagement {
    private static FacultyDAO facultyDAO = new FacultyDAO();
    private static StudentDAO studentDAO = new StudentDAO();
    private static GlobalSettingDAO gsDAO = new GlobalSettingDAO();

    /**
     * Registers a new faculty member.
     *
     * @param faculty the Professor object representing the new faculty member
     * @throws Exception if an error occurs during the registration process
     */
    public static void facultySignUp(Professor faculty) throws Exception {
      facultyDAO.addFaculty(faculty);
  }

  /**
     * Registers a new student.
     *
     * @param student the Student object representing the new student
     */
  public static void studentSignUp(Student student){
      studentDAO.addStudent(student);
  }

  /**
     * Removes a faculty member.
     *
     * @param userid the ID of the faculty member to be removed
     */
  public static void removeFaculty(String userid) {
    facultyDAO.deleteFaculty(userid);
  }

  /**
     * Removes a student.
     *
     * @param userid the ID of the student to be removed
     */
  public static void removeStudent(String userid) {
    studentDAO.deleteStudent(userid);
  }


  /**
     * Updates the information of a faculty member.
     *
     * @param faculty the Professor object representing the updated faculty information
     */
  public static void updateFaculty(Professor faculty) {
    facultyDAO.updateFaculty(faculty);
  }

  /**
     * Updates the information of a student.
     *
     * @param student the Student object representing the updated student information
     */
  public static void updateStudent(Student student) {
    studentDAO.updateStudent(student);
  }

  /**
     * Retrieves a student by their ID.
     *
     * @param userid the ID of the student to retrieve
     * @return the Student object corresponding to the provided ID, or null if not found
     */
  public static Student getStudentByID(String userid) {
    return studentDAO.queryStudentById(userid);
  }

  /**
     * Retrieves a list of students with a given legal name.
     *
     * @param legalName the legal name to search for
     * @return a list of Student objects with the specified legal name
     */
  public static List<Student> getStudentByLegalName(String legalName) {
    return studentDAO.queryStudentByLegalName(legalName);
  }

  /**
     * Retrieves a faculty member by their ID.
     *
     * @param userid the ID of the faculty member to retrieve
     * @return the Professor object corresponding to the provided ID, or null if not found
     */
  public static Professor getFacultyByID(String userid) {
    return facultyDAO.queryFacultyById(userid);
  }

  /**
     * Retrieves a list of faculty members with a given legal name.
     *
     * @param legalName the legal name to search for
     * @return a list of Professor objects with the specified legal name
     */
  public static List<Professor> getFacultyByLegalName(String legalName) {
    return facultyDAO.queryFacultyByLegalName(legalName);
  }


  /**
     * Checks if a faculty member exists based on their user ID.
     *
     * @param userid the ID of the faculty member to check
     * @return true if a faculty member with the provided ID exists, false otherwise
     */
  public static boolean checkFacultyExistsByID(String userid) {
    if(facultyDAO.queryFacultyById(userid) == null) {
        return false;
    }
    return true;
}

/**
     * Checks if a faculty member exists based on their email.
     *
     * @param email the email of the faculty member to check
     * @return true if a faculty member with the provided email exists, false otherwise
     */
public static boolean checkFacultyExistsByEmail(String email) {
  if(facultyDAO.queryFacultyByEmail(email) == null) {
      return false;
  }
  return true;
}

/**
     * Checks if a faculty member exists based on their legal name.
     *
     * @param legalName the legal name of the faculty member to check
     * @return true if a faculty member with the provided legal name exists, false otherwise
     */
public static boolean checkFacultyExistsByLegalName(String legalName) {
  if(facultyDAO.queryFacultyByLegalName(legalName) == null) {
      return false;
  }
  return true;
}

  /**
     * Checks if a student exists based on their user ID.
     *
     * @param userid the ID of the student to check
     * @return true if a student with the provided ID exists, false otherwise
     */
  public static boolean checkStudentExistsByID(String userid) {
    if(studentDAO.queryStudentById(userid) == null) {
        return false;
    }
    return true;
  }

  /**
     * Checks if a student exists based on their email.
     *
     * @param email the email of the student to check
     * @return true if a student with the provided email exists, false otherwise
     */
  public static boolean checkStudentExistsByEmail(String email) {
    if(studentDAO.queryStudentByEmail(email) == null) {
        return false;
    }
    return true;
  }

  /**
     * Checks if a student exists based on their legal name.
     *
     * @param legalName the legal name of the student to check
     * @return true if a student with the provided legal name exists, false otherwise
     */
  public static boolean checkStudentExistsByLegalName(String legalName) {
    if(studentDAO.queryStudentByLegalName(legalName).size() == 0) {
        return false;
    }
    return true;
  }

  /**
     * Retrieves the modification count for the display name permission.
     *
     * @return the number of modifications made to the display name permission setting
     */
  public static int getDisplayNamePermissionModificationCount() {
    return gsDAO.queryModificationCount("allow_modify_display_name");
  }

  /**
     * Sets the display name permission.
     *
     * @param permission the new value for the display name permission
     */
  public static void setDisplayNamePermission(boolean permission) {
    gsDAO.updateDisplayNamePermission(permission);
  }

  /**
     * Retrieves the current value of the display name permission.
     *
     * @return true if modifying the display name is allowed, false otherwise
     */
  public static boolean getDisplayNamePermission() {
    return gsDAO.queryDisplayNamePermission();
  }

  /**
     * Retrieves a set containing all faculty members.
     *
     * @return a set of Professor objects representing all faculty members
     */
  public static Set<Professor> getAllFaculty() {
    return facultyDAO.queryAllFaculty();
  }

  /**
     * Retrieves a set containing all students.
     *
     * @return a set of Student objects representing all students
     */
  public static Set<Student> getAllStudent() {
    return studentDAO.queryAllStudents();
  }

}
