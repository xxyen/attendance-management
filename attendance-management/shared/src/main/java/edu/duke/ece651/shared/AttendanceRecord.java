package edu.duke.ece651.shared;

public class AttendanceRecord {
    private Student student;
    private Status status;

    public AttendanceRecord(Student student, Status status) {
        this.student = student;
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public Status getStatus() {
        return status;
    }
}
