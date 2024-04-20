package edu.duke.ece651.shared;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import edu.duke.ece651.shared.model.*;
import java.util.Map;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.service.*;
import edu.duke.ece651.shared.*;


/**
 * An implementation of the Exporter interface for exporting session data to XML
 * format.
 */
public class XMLExporter implements Exporter {
    @Override
    public void exportAttendanceDataForProfessor(int sectionId, String filePath) throws IOException {
        AttendanceRecordService service = new AttendanceRecordService();
        SessionDAO sessionDAO = new SessionDAO();
        AttendanceRecordDAO attendanceRecordDAO = new AttendanceRecordDAO();
        StudentDAO studentDAO = new StudentDAO();

        Map<String, Double> attendanceScores = service.calculateSectionScores(sectionId);
        List<Session> sessions = sessionDAO.listSessionsBySection(sectionId);
        
        StringBuilder xmlBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlBuilder.append("<sectionData>\n");
        
        xmlBuilder.append(String.format("\t<sectionId>%s</sectionId>\n", sectionId));


        xmlBuilder.append("\t<attendanceScores>\n");
        for(Map.Entry<String, Double> entry : attendanceScores.entrySet()) {
            xmlBuilder.append("\t\t<score>\n");
            xmlBuilder.append(String.format("\t\t\t<studentId>%s</studentId>\n", entry.getKey()));
            xmlBuilder.append(String.format("\t\t\t<value>%.2f</value>\n", entry.getValue()));
            xmlBuilder.append("\t\t</score>\n");
        }
        xmlBuilder.append("\t</attendanceScores>\n");
        
        xmlBuilder.append("\t<sessions>\n");
        for(Session session : sessions) {
            xmlBuilder.append(String.format("\t\t<session id=\"%d\">\n", session.getSessionId()));
            xmlBuilder.append(String.format("\t\t\t<date>%s</date>\n", session.getSessionDate().toString()));
            // xmlBuilder.append(String.format("\t\t\t<startTime>%s</startTime>\n", session.getStartTime().toString()));
            // xmlBuilder.append(String.format("\t\t\t<endTime>%s</endTime>\n", session.getEndTime().toString()));

            List<AttendanceRecord> records = attendanceRecordDAO.listAttendanceBySession(session.getSessionId());
            for(AttendanceRecord record : records) {
                xmlBuilder.append("\t\t\t<attendanceRecord>\n");
                Student findStud = studentDAO.queryStudentById(record.getStudentId());
                xmlBuilder.append(String.format("\t\t\t\t<studentId>%s</studentId>\n", record.getStudentId()));
                xmlBuilder.append(String.format("\t\t\t\t<studentDisplayName>%s</studentDisplayName>\n", findStud.getDisplayName()));
                xmlBuilder.append(String.format("\t\t\t\t<status>%s</status>\n", String.valueOf(record.getStatus().getStatus())));
                xmlBuilder.append("\t\t\t</attendanceRecord>\n");
            }
            xmlBuilder.append("\t\t</session>\n");
        }
        xmlBuilder.append("\t</sessions>\n");
        
        xmlBuilder.append("</sectionData>");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xmlBuilder.toString());
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

        StringBuilder xmlBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlBuilder.append("<studentAttendance>\n");
        xmlBuilder.append(String.format("\t<studentId>%s</studentId>\n", studentId));
        xmlBuilder.append(String.format("\t<studentDisplayName>%s</studentDisplayName>\n", student.getDisplayName()));
        xmlBuilder.append(String.format("\t<attendanceScore>%.2f</attendanceScore>\n", totalScore));

        xmlBuilder.append("\t<sessions>\n");
        for (AttendanceRecord record : records) {
            Session session = sessionDAO.findSessionById(record.getSessionId());
            xmlBuilder.append("\t\t<session>\n");
            xmlBuilder.append(String.format("\t\t\t<sessionId>%d</sessionId>\n", session.getSessionId()));
            xmlBuilder.append(String.format("\t\t\t<sessionDate>%s</sessionDate>\n", session.getSessionDate().toString()));
            xmlBuilder.append(String.format("\t\t\t<status>%s</status>\n", String.valueOf(record.getStatus().getStatus())));
            xmlBuilder.append("\t\t</session>\n");
        }
        xmlBuilder.append("\t</sessions>\n");
        xmlBuilder.append("</studentAttendance>");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xmlBuilder.toString());
        }
    }

}
