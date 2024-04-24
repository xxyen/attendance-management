package duke.edu.ece651.studentclient.model;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.util.List;

/**
 * Represents a session within a course, including its ID, time, and attendance
 * records.
 */
public class Session {
    private int sessionId;
    private int sectionId;
    private Date sessionDate;
    private Time startTime;
    private Time endTime;

    // Coustuctors
    public Session(){}

    public Session(int sectionId, Date sessionDate, Time startTime, Time endTime){
        this.sectionId = sectionId;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Session(int sessionId, int sectionId, Date sessionDate, Time startTime, Time endTime){
        this.sessionId = sessionId;
        this.sectionId = sectionId;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sessionDate != null ? dateFormat.format(sessionDate) : "N/A";
        String startTimeStr = startTime != null ? startTime.toString() : "N/A";
        String endTimeStr = endTime != null ? endTime.toString() : "N/A";
        
        return "Session{" +
                "sessionId=" + sessionId +
                ", sectionId=" + sectionId +
                ", sessionDate=" + dateStr +
                ", startTime=" + startTimeStr +
                ", endTime=" + endTimeStr +
                '}';
    }
    
}
