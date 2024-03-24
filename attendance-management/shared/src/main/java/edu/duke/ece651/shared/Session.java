package edu.duke.ece651.shared;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Session {
    private final String courseid;
    private final Date time;
    private ArrayList<AttendanceRecord> records;

    public Session(String courseid, Date time) {
        this.courseid = courseid;
        this.time = time;
        this.records = new ArrayList<>();
    }

    public String getCourseid() {
        return courseid;
    }

    public Date getTime() {
        return time;
    }

    public List<AttendanceRecord> getRecords() {
        return records;
    }

    public void addRecord(AttendanceRecord record) {
        this.records.add(record);
    }

    public void setRecords(ArrayList<AttendanceRecord> records) {
        this.records = records;
    }
    ////////////// Added by Jiazheng Sun///////////////

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
    /////////////////////////////////////////////////

    public void saveAttendanceRecords() throws Exception {
        String workingDir = System.getProperty("user.dir");
        String DATA_PATH = workingDir + "/data/" + courseid + "/sessions/";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        String fileName = dateFormat.format(time) + ".txt";
        // File file = new File(DATA_PATH + fileName);

        // try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        // for (AttendanceRecord record : records) {
        // writer.write(record.getStudent().getStudentID() + ", " +
        // record.getStatus().getStatus());
        // writer.newLine();
        // }
        // }
        String tempPath = DATA_PATH + fileName + ".temp";

        File file = new File(tempPath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (AttendanceRecord record : records) {
                writer.write(record.getStudent().getPersonalID() + ", " + record.getStatus().getStatus());
                writer.newLine();
            }
        }

        FileEncryptorDecryptor.encrypt(tempPath, DATA_PATH + fileName);

        // Uncomment this line when go to production !!!
        // new File(tempPath).delete();
    }
}
