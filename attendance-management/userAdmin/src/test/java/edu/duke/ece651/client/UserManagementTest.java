package edu.duke.ece651.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.userAdmin.UserManagement;
import edu.duke.ece651.shared.Email;
import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.Student;

public class UserManagementTest {
  @Test
  public void test_faculty() throws Exception{
    String userid = "sdfkhj8923hiuhuiDHjhoidf78923h";
    String email = "guigy@uigyhjg.eee";
    Professor faculty = new Professor(userid, userid, new Email(email));
    UserManagement.facultySignUp(faculty);
    UserManagement.updateFaculty(faculty);
    UserManagement.getFacultyByID(userid);
    UserManagement.getFacultyByLegalName(userid);
    UserManagement.getAllFaculty();
    UserManagement.checkFacultyExistsByID(userid);
    UserManagement.checkFacultyExistsByEmail(email);
    UserManagement.checkFacultyExistsByLegalName(userid);
    UserManagement.removeFaculty(userid);
    UserManagement.checkFacultyExistsByID(userid);
    UserManagement.checkFacultyExistsByEmail(email);
    UserManagement.checkFacultyExistsByLegalName(userid);

  }

  @Test
  public void test_student() {
    String userid = "sdfkhj8923hiuhuiDHjhoidsdf4f78923h";
    String email = "gui2gy@uig4yhjg.egee";
    Student student = new Student(userid, userid, userid, new Email(email));
    UserManagement.studentSignUp(student);
    UserManagement.updateStudent(student);
    UserManagement.getStudentByID(userid);
    UserManagement.getStudentByLegalName(userid);
    UserManagement.getAllStudent();
    UserManagement.checkStudentExistsByID(userid);
    UserManagement.checkStudentExistsByLegalName(userid);
    UserManagement.checkStudentExistsByEmail(email);
    UserManagement.removeStudent(userid);
    UserManagement.checkStudentExistsByID(userid);
    UserManagement.checkStudentExistsByLegalName(userid);
    UserManagement.checkStudentExistsByEmail(email);
  }

  @Test
  public void test_permissionSetting() {
    boolean p = UserManagement.getDisplayNamePermission();
    int cnt = UserManagement.getDisplayNamePermissionModificationCount();
    //UserManagement.setDisplayNamePermission(p);
  }

}
