package edu.duke.ece651.shared;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.json.*;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.service.*;
import edu.duke.ece651.shared.*;


/**
 * An implementation of the Exporter interface for exporting session data to
 * JSON format.
 */
public class JSONExporter implements Exporter {
    @Override
    public void exportAttendanceDataForProfessor(int sectionId, String filePath) throws IOException {
        AttendanceRecordService service = new AttendanceRecordService();
        SessionDAO sessionDAO = new SessionDAO();
        AttendanceRecordDAO attendanceRecordDAO = new AttendanceRecordDAO();
        StudentDAO studentDAO = new StudentDAO();

        Map<String, Double> attendanceScores = service.calculateSectionScores(sectionId);
        List<Session> sessions = sessionDAO.listSessionsBySection(sectionId);
        
        JSONObject exportObj = new JSONObject();
        JSONArray scoresArray = new JSONArray();

        exportObj.put("sectionId", sectionId);
        
        for(Map.Entry<String, Double> entry : attendanceScores.entrySet()) {
            JSONObject scoreObj = new JSONObject();
            scoreObj.put("studentId", entry.getKey());
            scoreObj.put("score", entry.getValue());
            scoresArray.put(scoreObj);
        }
        exportObj.put("attendanceScores", scoresArray);
        
        JSONArray sessionsArray = new JSONArray();
        for(Session session : sessions) {
            JSONObject sessionObj = new JSONObject();
            sessionObj.put("sessionId", session.getSessionId());
            sessionObj.put("sessionDate", session.getSessionDate().toString());
            // sessionObj.put("sessionStartTime", session.getStartTime().toString());
            // sessionObj.put("sessionEndTime", session.getEndTime().toString());
            List<AttendanceRecord> records = attendanceRecordDAO.listAttendanceBySession(session.getSessionId());
            
            JSONArray recordsArray = new JSONArray();
            for(AttendanceRecord record : records) {
                JSONObject recordObj = new JSONObject();

                Student findStud = studentDAO.queryStudentById(record.getStudentId());
                recordObj.put("studentId", record.getStudentId());
                recordObj.put("studentDisplayName", findStud.getDisplayName());
                recordObj.put("status", String.valueOf(record.getStatus().getStatus()));
                recordsArray.put(recordObj);
            }
            sessionObj.put("attendanceRecords", recordsArray);
            sessionsArray.put(sessionObj);
        }
        exportObj.put("sessions", sessionsArray);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(exportObj.toString(4));
        }
    }

    @Override
    public void exportAttendanceDataForStudent(String studentId, int sectionId, String filePath) throws IOException {
        StudentDAO studentDAO = new StudentDAO();
        AttendanceRecordService service = new AttendanceRecordService();
        AttendanceRecordDAO attendanceRecordDAO = new AttendanceRecordDAO();
        SessionDAO sessionDAO = new SessionDAO();
        
        Student student = studentDAO.queryStudentById(studentId);
        double totalScore = service.calculateStudentSectionScore(studentId, sectionId);
        List<AttendanceRecord> records = attendanceRecordDAO.listAttendanceByStudentInSection(studentId, sectionId);

        JSONObject exportObj = new JSONObject();
        exportObj.put("studentId", studentId);
        exportObj.put("studentDisplayName", student.getDisplayName());
        exportObj.put("attendanceScore", totalScore);

        JSONArray sessionsArray = new JSONArray();
        for (AttendanceRecord record : records) {
            Session session = sessionDAO.findSessionById(record.getSessionId());
            JSONObject sessionObj = new JSONObject();
            sessionObj.put("sessionId", session.getSessionId());
            sessionObj.put("sessionDate", session.getSessionDate().toString());
            sessionObj.put("status", String.valueOf(record.getStatus().getStatus()));
            sessionsArray.put(sessionObj);
        }
        exportObj.put("sessions", sessionsArray);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(exportObj.toString(4));
        }
    }

}
