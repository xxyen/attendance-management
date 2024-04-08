package edu.duke.ece651.shared.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.List;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.*;
import java.util.Date;
import java.sql.Time;

public class EnrollmentDAOTest {
  @Test
  public void test_enrollment() {
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    CourseDAO courseDAO = new CourseDAO();
    FacultyDAO facultyDAO = new FacultyDAO();
    SectionDAO sectionDAO = new SectionDAO();
    StudentDAO studentDAO = new StudentDAO();

    Course newCourse = new Course("CS105", "Introduction to CS");
    int result = courseDAO.addCourse(newCourse);

    Professor prof1 = new Professor("F005", "f01", new Email("f5@duke.edu"));
    facultyDAO.addFaculty(prof1);

    Student stud1 = new Student("S001", "legal stud01", "display stud01", new Email("stud1@duke.edu"));
    studentDAO.addStudent(stud1);

    Section newSection = new Section("CS105", "F005");
    sectionDAO.addSectionToCourse(newSection);
        
    Enrollment enrollment = new Enrollment(newSection.getSectionId(), "S001", new Date(), "enrolled", true);
    int addResult = enrollmentDAO.addEnrollment(enrollment);
    assertNotEquals(0, addResult);
    
    Enrollment queriedEnrollment = enrollmentDAO.queryEnrollmentById(enrollment.getEnrollmentId());
    assertEquals(queriedEnrollment.getStudentId(), "S001");
    
    queriedEnrollment.setStatus("dropped");
    int updateResult = enrollmentDAO.updateEnrollment(queriedEnrollment);
    assertEquals(queriedEnrollment.getStatus(), "dropped");
    
    Enrollment updatedEnrollment = enrollmentDAO.queryEnrollmentById(queriedEnrollment.getEnrollmentId());
    assertNotNull(updatedEnrollment);

    List<Enrollment> enrollments = enrollmentDAO.queryAllEnrollments();
    assertNotNull(enrollments);

    List<Enrollment> enrollmentsBySection = enrollmentDAO.listEnrollmentsBySection(newSection.getSectionId());
    assertNotNull(enrollmentsBySection);

    Enrollment findEnrollment = enrollmentDAO.findEnrollmentByStudentAndSection("S001", newSection.getSectionId());
    assertEquals(queriedEnrollment.getStatus(), "dropped");
    
    int deleteResult = enrollmentDAO.deleteEnrollment(queriedEnrollment.getEnrollmentId());
    
    Enrollment deletedEnrollment = enrollmentDAO.queryEnrollmentById(queriedEnrollment.getEnrollmentId());
    assertNull(deletedEnrollment);

    sectionDAO.deleteSection(newSection.getSectionId());
    courseDAO.deleteCourse("CS105");
    facultyDAO.deleteFaculty("F005");
    studentDAO.deleteStudent("S001");
  }

}
