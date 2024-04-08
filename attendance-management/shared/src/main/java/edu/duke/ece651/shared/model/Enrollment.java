package edu.duke.ece651.shared.model;

import java.util.Date;
import java.text.SimpleDateFormat;


public class Enrollment {
    private int enrollmentId;
    private int sectionId;
    private String studentId;
    private Date enrollmentDate;
    private String status;
    private boolean receiveNotifications; 

    public Enrollment() {}

    public Enrollment(int enrollmentId, int sectionId, String studentId, Date enrollmentDate, String status, boolean receiveNotifications) {
        this.enrollmentId = enrollmentId;
        this.sectionId = sectionId;
        this.studentId = studentId;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
        this.receiveNotifications = receiveNotifications;
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

    public boolean isReceiveNotifications() {
        return receiveNotifications;
    }

    public void setReceiveNotifications(boolean receiveNotifications) {
        this.receiveNotifications = receiveNotifications;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = enrollmentDate != null ? dateFormat.format(enrollmentDate) : "N/A";
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", sectionId=" + sectionId +
                ", studentId='" + studentId + '\'' +
                ", enrollmentDate=" + dateStr +
                ", status='" + status + '\'' +
                ", receiveNotifications=" + receiveNotifications +
                '}';
    }
}
