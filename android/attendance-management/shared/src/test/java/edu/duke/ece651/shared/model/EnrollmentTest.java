package edu.duke.ece651.shared.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnrollmentTest {

    @Test
    public void test_constructor() {
        Date enrollmentDate = new Date();
        Enrollment enrollment = new Enrollment(1, "student123", enrollmentDate, "enrolled", true);

        assertEquals(1, enrollment.getSectionId());
        assertEquals("student123", enrollment.getStudentId());
        assertEquals(enrollmentDate, enrollment.getEnrollmentDate());
        assertEquals("enrolled", enrollment.getStatus());
        assertTrue(enrollment.isReceiveNotifications());
    }

    @Test
    public void test_setters() {
        Date enrollmentDate = new Date();
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(10);
        enrollment.setSectionId(2);
        enrollment.setStudentId("student456");
        enrollment.setEnrollmentDate(enrollmentDate);
        enrollment.setStatus("dropped");
        enrollment.setReceiveNotifications(false);

        assertEquals(10, enrollment.getEnrollmentId());
        assertEquals(2, enrollment.getSectionId());
        assertEquals("student456", enrollment.getStudentId());
        assertEquals(enrollmentDate, enrollment.getEnrollmentDate());
        assertEquals("dropped", enrollment.getStatus());
        assertFalse(enrollment.isReceiveNotifications());
    }

    @Test
    public void test_toString() {
        Date enrollmentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = dateFormat.format(enrollmentDate);
        Enrollment enrollment = new Enrollment(3, "student789", enrollmentDate, "dropped", true);

        String expectedString = "Enrollment{" +
                "enrollmentId=0" +
                ", sectionId=" + 3 +
                ", studentId='student789'" +
                ", enrollmentDate=" + dateStr +
                ", status='dropped'" +
                ", receiveNotifications=" + true +
                '}';
        assertEquals(expectedString, enrollment.toString().substring(0, expectedString.length()));
    }
}
