package edu.duke.ece651.shared;

/**
 * Represents an attendance record for a student in a specific session.
 */
public class AttendanceRecord {
    private Student student;
    private Status status;

    /**
     * Constructs an AttendanceRecord with the specified student and their
     * attendance status.
     *
     * @param student the student this attendance record is for
     * @param status  the attendance status of the student
     */
    public AttendanceRecord(Student student, Status status) {
        this.student = student;
        this.status = status;
    }

    /**
     * Returns the student associated with this attendance record.
     *
     * @return the Student object
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Returns the attendance status of the student.
     *
     * @return the Status object
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Updates the attendance status of the student.
     *
     * @param newStatus the new status to be set
     */
    public void changeRecord(Status newStatus) {
        this.status = newStatus;
    }
}
