package edu.duke.ece651.shared.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AttendanceRecordTest {

    @Test
    public void test_defaultConstructor() {
        AttendanceRecord record = new AttendanceRecord();
        assertNull(record.getStatus()); 
    }

    @Test
    public void test_constructorWithParameters() {
        Status status = new Status('p');
        AttendanceRecord record = new AttendanceRecord(1, 100, "student01", status);

        assertEquals(1, record.getRecordId());
        assertEquals(100, record.getSessionId());
        assertEquals("student01", record.getStudentId());
        assertEquals(status, record.getStatus());
    }

    @Test
    public void test_constructorWithoutRecordId() {
        Status status = new Status('a');
        AttendanceRecord record = new AttendanceRecord(200, "student02", status);

        assertEquals(200, record.getSessionId());
        assertEquals("student02", record.getStudentId());
        assertEquals(status, record.getStatus());
    }

    @Test
    public void test_settersAndGetters() {
        AttendanceRecord record = new AttendanceRecord();
        Status status = new Status('t');

        record.setRecordId(3);
        record.setSessionId(300);
        record.setStudentId("student03");
        record.setStatus(status);

        assertEquals(3, record.getRecordId());
        assertEquals(300, record.getSessionId());
        assertEquals("student03", record.getStudentId());
        assertEquals('t', record.getStatus().getStatus());
    }

    @Test
    public void test_toString() {
        Status status = new Status('p');
        AttendanceRecord record = new AttendanceRecord(4, 400, "student04", status);
        
        String expectedString = "AttendanceRecord{" +
                "recordId=4" +
                ", sessionId=400" +
                ", studentId='student04'" +
                ", status=p" +
                '}';
        
        assertEquals(expectedString, record.toString());
    }
}
