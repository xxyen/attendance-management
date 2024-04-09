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
    public void exportAttendanceData(int sectionId, String filePath) throws IOException {
        AttendanceRecordService service = new AttendanceRecordService();
        SessionDAO sessionDAO = new SessionDAO();
        AttendanceRecordDAO attendanceRecordDAO = new AttendanceRecordDAO();

        Map<String, Double> attendanceScores = service.calculateSectionScores(sectionId);
        List<Session> sessions = sessionDAO.listSessionsBySection(sectionId);
        
        JSONObject exportObj = new JSONObject();
        JSONArray scoresArray = new JSONArray();
        
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
            sessionObj.put("sessionStartTime", session.getStartTime().toString());
            sessionObj.put("sessionEndTime", session.getEndTime().toString());
            List<AttendanceRecord> records = attendanceRecordDAO.listAttendanceBySession(session.getSessionId());
            
            JSONArray recordsArray = new JSONArray();
            for(AttendanceRecord record : records) {
                JSONObject recordObj = new JSONObject();
                recordObj.put("studentId", record.getStudentId());
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
}
