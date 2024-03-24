package edu.duke.ece651.shared;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class ExportService {
    public static void exportToFile(List<Session> sessions, String format, List<String> fields, String filePath)
            throws IOException {
        switch (format.toLowerCase()) {
            case "json":
                exportToJson(sessions, fields, filePath);
                break;
            case "xml":
                exportToXml(sessions, fields, filePath);
                break;
            case "custom":
                exportToCustom(sessions, fields, filePath);
                break;
            default:
                throw new IllegalArgumentException("Unsupported file format: " + format);
        }
    }

    private static void exportToJson(List<Session> sessions, List<String> fields, String filePath) throws IOException {
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
                    recordObj.put("studentID", record.getStudent().getPersonalID());
                }
                if (fields.contains("legalName")) {
                    recordObj.put("legalName", record.getStudent().getLegalName());
                }
                if (fields.contains("displayName")) {
                    recordObj.put("displayName", record.getStudent().getDisplayName());
                }
                if (fields.contains("email")) {
                    recordObj.put("email", record.getStudent().getEmailAddr().getEmailAddr());
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

    private static void exportToXml(List<Session> sessions, List<String> fields, String filePath) throws IOException {
        StringBuilder xmlBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlBuilder.append("<sessions>\n");
        for (Session session : sessions) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formattedTime = dateFormat.format(session.getTime());

            xmlBuilder.append(
                    String.format("\t<session courseId=\"%s\" time=\"%s\">\n", session.getCourseid(), formattedTime));
            for (AttendanceRecord record : session.getRecords()) {
                xmlBuilder.append("\t\t<attendanceRecord>\n");
                if (fields.contains("studentID")) {
                    xmlBuilder.append(
                            String.format("\t\t\t<studentID>%s</studentID>\n", record.getStudent().getPersonalID()));
                }
                if (fields.contains("legalName")) {
                    xmlBuilder.append(
                            String.format("\t\t\t<legalName>%s</legalName>\n", record.getStudent().getLegalName()));
                }
                if (fields.contains("displayName")) {
                    xmlBuilder.append(String.format("\t\t\t<displayName>%s</displayName>\n",
                            record.getStudent().getDisplayName()));
                }
                if (fields.contains("email")) {
                    xmlBuilder.append(String.format("\t\t\t<email>%s</email>\n",
                            record.getStudent().getEmailAddr().getEmailAddr()));
                }
                if (fields.contains("status")) {
                    xmlBuilder.append(String.format("\t\t\t<status>%s</status>\n", record.getStatus().getStatus()));
                }
                xmlBuilder.append("\t\t</attendanceRecord>\n");
            }
            xmlBuilder.append("\t</session>\n");
        }
        xmlBuilder.append("</sessions>");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xmlBuilder.toString());
        }
    }

    private static void exportToCustom(List<Session> sessions, List<String> fields, String filePath)
            throws IOException {
        // custom format
    }
}
