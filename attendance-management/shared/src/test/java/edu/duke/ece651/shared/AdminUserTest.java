package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.shared.AdminUser;

public class AdminUserTest {
  private AdminUser admin = AdminUser.getInstance();
  Email e1 = new Email("abc@gmail.com");
  Email e2 = new Email("def@duke.edu");
  
  @Test
  public void test_getters() {
    assertEquals(admin.getUserid(), "admin");
    assertEquals(admin.getPassword(), "SthYouDonotKnow77890");
    assertEquals(admin.getUserType(), "administrator");
    assertNull(admin.getEmail());
    assertTrue(admin.isCorrectPassword("SthYouDonotKnow77890"));
    assertFalse(admin.setPwd(""));
    assertFalse(admin.isDefaultPwd());
  }

  @Test
  public void test_studentSignUpandUpdates() {
    Student stu1 = new Student("cp357", "Can Pei", "Alex Pei", e1); 
    assertTrue(admin.studentSignUp(stu1));
    assertFalse(admin.studentSignUp(stu1));
    Student stu2 = new Student("cp357", "Can Pei", "Alex", e2);
    admin.updateStudent(stu2);
    //assertEquals(admin.getStudentByID("cp357"), stu2);
    admin.removeStudent("cp357");
  }

  @Test
  public void test_facultySignUpandUpdates() {
    Professor prof1 = new Professor("cp357", "Can Pei", e1);
    assertTrue(admin.facultySignUp(prof1));
    assertFalse(admin.facultySignUp(prof1));
    Professor prof2 = new Professor("cp357", "Alex", e2);
    admin.updateFaculty(prof2);
    //assertEquals(admin.getFacultyByID("cp357"), prof2); 
    admin.removeFaculty("cp357"); 
  }
}
