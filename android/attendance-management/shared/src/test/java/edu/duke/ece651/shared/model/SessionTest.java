package edu.duke.ece651.shared.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SessionTest {

    @Test
    public void test_constructor() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        
        int sectionId = 101;
        String dateString = "2024-04-05";
        String startTimeString = "09:00:00";
        String endTimeString = "11:00:00";
        
        java.util.Date date = dateFormat.parse(dateString);
        Time startTime = new Time(timeFormat.parse(startTimeString).getTime());
        Time endTime = new Time(timeFormat.parse(endTimeString).getTime());

        Session session = new Session(sectionId, date, startTime, endTime);

        assertEquals(sectionId, session.getSectionId());
        assertEquals(date, session.getSessionDate());
        assertEquals(startTime, session.getStartTime());
        assertEquals(endTime, session.getEndTime());
    }

    @Test
    public void test_setters() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        
        Session session = new Session();
        
        int sessionId = 10;
        int sectionId = 102;
        String dateString = "2024-04-06";
        String startTimeString = "10:00:00";
        String endTimeString = "12:00:00";
        
        java.util.Date date = dateFormat.parse(dateString);
        Time startTime = new Time(timeFormat.parse(startTimeString).getTime());
        Time endTime = new Time(timeFormat.parse(endTimeString).getTime());
        
        session.setSessionId(sessionId);
        session.setSectionId(sectionId);
        session.setSessionDate(date);
        session.setStartTime(startTime);
        session.setEndTime(endTime);

        assertEquals(sessionId, session.getSessionId());
        assertEquals(sectionId, session.getSectionId());
        assertEquals(date, session.getSessionDate());
        assertEquals(startTime, session.getStartTime());
        assertEquals(endTime, session.getEndTime());
    }

    @Test
    public void test_toString() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        int sectionId = 103;
        String dateString = "2024-04-07";
        String startTimeString = "11:00:00";
        String endTimeString = "13:00:00";
        
        java.util.Date date = dateFormat.parse(dateString);
        Time startTime = new Time(timeFormat.parse(startTimeString).getTime());
        Time endTime = new Time(timeFormat.parse(endTimeString).getTime());
        
        Session session = new Session(sectionId, date, startTime, endTime);
        
        String expectedString = String.format("Session{sessionId=0, sectionId=%d, sessionDate=%s, startTime=%s, endTime=%s}",
                sectionId, dateFormat.format(date), startTime.toString(), endTime.toString());

        assertEquals(expectedString, session.toString());
    }
}
