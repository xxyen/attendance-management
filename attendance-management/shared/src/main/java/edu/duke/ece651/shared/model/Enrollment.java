package edu.duke.ece651.shared.model;

import java.util.Date;

public class Enrollment {
    private int enrollmentId;
    private int sectionId;
    private String studentId;
    private Date enrollmentDate;
    private String status;

    public Enrollment() {}

    public Enrollment(int enrollmentId, int sectionId, String studentId, Date enrollmentDate, String status) {
        this.enrollmentId = enrollmentId;
        this.sectionId = sectionId;
        this.studentId = studentId;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
