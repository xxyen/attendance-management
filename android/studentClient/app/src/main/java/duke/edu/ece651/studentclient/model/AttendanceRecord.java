package duke.edu.ece651.studentclient.model;

import duke.edu.ece651.studentclient.*;

/**
 * Represents an attendance record for a student in a specific session.
 */
public class AttendanceRecord {
    private int recordId;
    private int sessionId;
    private String studentId;
    private Status status; 

    // Constructors, Getters, and Setters
    public AttendanceRecord() {
    }
    
    public AttendanceRecord(int recordId, int sessionId, String studentId, Status status){
        this.recordId = recordId;
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.status = status;
    }

    public AttendanceRecord(int sessionId, String studentId, Status status){
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.status = status;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" +
                "recordId=" + recordId +
                ", sessionId=" + sessionId +
                ", studentId='" + studentId + '\'' +
                ", status=" + status.getStatus() +
                '}';
    }
}
