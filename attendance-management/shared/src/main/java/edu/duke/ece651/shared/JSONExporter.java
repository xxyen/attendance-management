package edu.duke.ece651.shared;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.json.*;

/**
 * An implementation of the Exporter interface for exporting session data to
 * JSON format.
 */
public class JSONExporter implements Exporter {
    @Override
    public void export(List<Session> sessions, List<String> fields, String filePath) throws IOException {
        JSONArray sessionArray = new JSONArray();
        for (Session session : sessions) {
            JSONObject sessionObj = new JSONObject();
            sessionObj.put("courseId", session.getCourseid());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formattedTime = dateFormat.format(session.getTime());
            sessionObj.put("sessionTime", formattedTime);

            JSONArray recordArray = new JSONArray();
            for (AttendanceRecord record : session.getRecords()) {
                JSONObject recordObj = new JSONObject();
                if (fields.contains("studentID")) {
                    recordObj.put("studentID", record.getStudent().getUserid());
                }
                if (fields.contains("legalName")) {
                    recordObj.put("legalName", record.getStudent().getLegalName());
                }
                if (fields.contains("displayName")) {
                    recordObj.put("displayName", record.getStudent().getDisplayName());
                }
                if (fields.contains("email")) {
                    recordObj.put("email", record.getStudent().getEmail().getEmailAddr());
                }
                if (fields.contains("status")) {
                    recordObj.put("status", String.valueOf(record.getStatus().getStatus()));
                }
                recordArray.put(recordObj);
            }
            sessionObj.put("attendanceRecords", recordArray);
            sessionArray.put(sessionObj);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(sessionArray.toString());
        }
    }
}
