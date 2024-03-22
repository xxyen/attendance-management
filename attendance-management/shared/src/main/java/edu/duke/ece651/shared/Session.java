package edu.duke.ece651.shared;

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

  //////////////Added by Jiazheng Sun///////////////

  public boolean changeRecord(Student student, Status newStatus) {
    Iterable<AttendanceRecord> result = records;
    for (AttendanceRecord rec: result) {
      if (rec.getStudent().equals(student)) {
        rec.changeRecord(newStatus);
        return true;
      }
    }
    return false;
  }
  /////////////////////////////////////////////////
}
