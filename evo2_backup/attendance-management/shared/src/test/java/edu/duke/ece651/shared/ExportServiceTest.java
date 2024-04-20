package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Disabled;


import org.junit.jupiter.api.Test;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.service.*;
import edu.duke.ece651.shared.*;
import java.util.Date;
import java.sql.Time;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ExportServiceTest {
    @Test
    public void testExportToJson() throws IOException, ParseException {

        CourseDAO courseDAO = new CourseDAO();
        SectionDAO sectionDAO = new SectionDAO();
        FacultyDAO facultyDAO = new FacultyDAO();
        StudentDAO studentDAO = new StudentDAO();
        SessionDAO sessionDAO = new SessionDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        AttendanceRecordDAO attendanceRecordDAO = new AttendanceRecordDAO();

        AttendanceRecordService service = new AttendanceRecordService();

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

        Session newSession2 = new Session(newSection.getSectionId(), new Date(), Time.valueOf("13:00:00"), Time.valueOf("15:00:00"));
        sessionDAO.addSession(newSession2);
        Status status2 = new Status('a');
        AttendanceRecord record2 = new AttendanceRecord(newSession2.getSessionId(), "S00A", status2);
        attendanceRecordDAO.addAttendanceRecord(record2);

        Double studScoreBySectionBefore = service.calculateStudentSectionScore("S00A", newSection.getSectionId());
        assertEquals(studScoreBySectionBefore, 0);

        Status newStatus = new Status('p');
        record.setStatus(newStatus);
        attendanceRecordDAO.updateAttendanceRecord(record);
        assertEquals(newStatus.getStatus(), record.getStatus().getStatus());

        String workingDir = System.getProperty("user.dir");
        String filePath1 = workingDir + "/export/" + "test_professor_record1.json";
        String filePath2 = workingDir + "/export/" + "test_professor_record1.xml";
        String filePath3 = workingDir + "/export/" + "test_student_record1.json";
        String filePath4 = workingDir + "/export/" + "test_student_record1.xml";

        ExportService exportFile = new ExportService();
        exportFile.exportSectionAttendanceDataForProfessor(newSection.getSectionId(), "json", filePath1);
        exportFile.exportSectionAttendanceDataForProfessor(newSection.getSectionId(), "xml", filePath2);
        exportFile.exportSectionAttendanceDataForStudent("S00A", newSection.getSectionId(), "json", filePath3);
        exportFile.exportSectionAttendanceDataForStudent("S00A",newSection.getSectionId(), "xml", filePath4);

        attendanceRecordDAO.deleteAttendanceRecord(record.getRecordId());
        attendanceRecordDAO.deleteAttendanceRecord(record2.getRecordId());
        sessionDAO.deleteSession(newSession.getSessionId());
        sessionDAO.deleteSession(newSession2.getSessionId());
        enrollmentDAO.deleteEnrollment(enrollment.getEnrollmentId());
        sectionDAO.deleteSection(newSection.getSectionId());
        facultyDAO.deleteFaculty("F00A");
        studentDAO.deleteStudent("S00A");
        courseDAO.deleteCourse("CS10A");
  }

}
