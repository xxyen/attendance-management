package edu.duke.ece651.shared;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a session within a course, including its ID, time, and attendance
 * records.
 */
public class Session {
    private final String courseid;
    private final Date time;
    private ArrayList<AttendanceRecord> records;

    /**
     * Constructs a Session object with specified course ID and time.
     *
     * @param courseid the unique identifier for the course
     * @param time     the specific date and time the session occurs
     */
    public Session(String courseid, Date time) {
        this.courseid = courseid;
        this.time = time;
        this.records = new ArrayList<>();
    }

    /**
     * Returns the course ID associated with this session.
     *
     * @return a string representing the course ID
     */
    public String getCourseid() {
        return courseid;
    }

    /**
     * Returns the time of the session.
     *
     * @return a Date object representing the session's date and time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Returns the list of attendance records for this session.
     *
     * @return a list of AttendanceRecord objects
     */
    public List<AttendanceRecord> getRecords() {
        return records;
    }

    /**
     * Adds a record to the session's list of attendance records.
     *
     * @param record the AttendanceRecord to add
     */
    public void addRecord(AttendanceRecord record) {
        this.records.add(record);
    }

    /**
     * Sets the list of attendance records for this session.
     *
     * @param records the list of AttendanceRecord objects to set
     */
    public void setRecords(ArrayList<AttendanceRecord> records) {
        this.records = records;
    }

    /**
     * Changes the attendance status for a specified student in this session.
     *
     * @param student   the student whose attendance record is to be changed
     * @param newStatus the new attendance status for the student
     * @return true if the record was successfully changed, false otherwise
     */
    public boolean changeRecord(Student student, Status newStatus) {
        Iterable<AttendanceRecord> result = records;
        for (AttendanceRecord rec : result) {
            if (rec.getStudent().equals(student)) {
                rec.changeRecord(newStatus);
                return true;
            }
        }
        return false;
    }

    /**
     * Saves the session's attendance records to a file in a specified directory.
     *
     * @throws Exception if an error occurs during file writing
     */
    public void saveAttendanceRecords() throws Exception {
        String workingDir = System.getProperty("user.dir");
        String DATA_PATH = workingDir + "/data/" + courseid + "/sessions/";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        String fileName = dateFormat.format(time) + ".txt";
        // for test only
        // File file = new File(DATA_PATH + fileName);

        // comment 2 lines for test
        String tempPath = DATA_PATH + fileName + ".temp";

        File file = new File(tempPath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (AttendanceRecord record : records) {
                writer.write(record.getStudent().getUserid() + "," + record.getStatus().getStatus());
                writer.newLine();
            }
        }

        // comment 1 line for test
        FileEncryptorDecryptor.encrypt(tempPath, DATA_PATH + fileName);

        // Uncomment this line when go to production !!!
        // new File(tempPath).delete();
    }
}
