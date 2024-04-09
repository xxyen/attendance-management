package edu.duke.ece651.shared.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.*;
import java.util.Date;
import java.sql.Time;
import java.util.List;

public class AttendanceRecordDAOTest {
  @Test
  public void test_attendanceRecord() {
    CourseDAO courseDAO = new CourseDAO();
    SectionDAO sectionDAO = new SectionDAO();
    FacultyDAO facultyDAO = new FacultyDAO();
    StudentDAO studentDAO = new StudentDAO();
    SessionDAO sessionDAO = new SessionDAO();
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    AttendanceRecordDAO attendanceRecordDAO = new AttendanceRecordDAO();

    Course newCourse = new Course("CS10A", "Introduction to CS");
    int result = courseDAO.addCourse(newCourse);

    Professor prof1 = new Professor("F00A", "f0A", new Email("fA@duke.edu"));
    facultyDAO.addFaculty(prof1);

    Student stud1 = new Student("S00A", "legal stud0A", "display stud0A", new Email("studA@duke.edu"));
    studentDAO.addStudent(stud1);

    Section newSection = new Section("CS10A", "F00A");
    sectionDAO.addSectionToCourse(newSection);

    Enrollment enrollment = new Enrollment(newSection.getSectionId(), "S00A", new Date(), "enrolled", true);
    enrollmentDAO.addEnrollment(enrollment);

    Session newSession = new Session(newSection.getSectionId(), new Date(), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"));
    sessionDAO.addSession(newSession);
    

    Status status = new Status('a');
    AttendanceRecord record = new AttendanceRecord(newSession.getSessionId(), "S00A", status);
    result = attendanceRecordDAO.addAttendanceRecord(record);
    assertTrue(record.getRecordId() > 0);

    Status newStatus = new Status('p');
    record.setStatus(newStatus);
    attendanceRecordDAO.updateAttendanceRecord(record);
    assertEquals(newStatus.getStatus(), record.getStatus().getStatus());

    List<AttendanceRecord> recordBySession = attendanceRecordDAO.listAttendanceBySession(newSession.getSessionId());
    assertEquals(recordBySession.get(0).getStudentId(), "S00A");
    List<AttendanceRecord> recordBySection = attendanceRecordDAO.listAttendanceBySection(newSection.getSectionId());
    assertEquals(recordBySection.get(0).getStudentId(), "S00A");
    List<AttendanceRecord> recordByStudentInSection = attendanceRecordDAO.listAttendanceByStudentInSection("S00A",newSection.getSectionId());
    assertEquals(recordByStudentInSection.get(0).getStudentId(), "S00A");
    AttendanceRecord recordBySessionAndStudent = attendanceRecordDAO.findAttendanceRecordBySessionAndStudent(newSession.getSessionId(), "S00A");
    assertEquals(recordBySessionAndStudent.getStudentId(), "S00A");
    List<AttendanceRecord> recordByStudent = attendanceRecordDAO.listAttendanceByStudent("S00A");
    assertEquals(recordByStudent.get(0).getStudentId(), "S00A");


    attendanceRecordDAO.deleteAttendanceRecord(record.getRecordId());
    sessionDAO.deleteSession(newSession.getSessionId());
    enrollmentDAO.deleteEnrollment(enrollment.getEnrollmentId());
    sectionDAO.deleteSection(newSection.getSectionId());
    facultyDAO.deleteFaculty("F00A");
    studentDAO.deleteStudent("S00A");
    courseDAO.deleteCourse("CS10A");
    
    
  }

}
