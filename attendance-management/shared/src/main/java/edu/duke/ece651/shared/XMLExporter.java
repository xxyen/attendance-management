package edu.duke.ece651.shared;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import edu.duke.ece651.shared.model.*;


/**
 * An implementation of the Exporter interface for exporting session data to XML
 * format.
 */
// public class XMLExporter implements Exporter {
//     @Override
//     public void export(List<Session> sessions, List<String> fields, String filePath) throws IOException {
//         StringBuilder xmlBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//         xmlBuilder.append("<sessions>\n");
//         for (Session session : sessions) {
//             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//             String formattedTime = dateFormat.format(session.getTime());

//             xmlBuilder.append(
//                     String.format("\t<session courseId=\"%s\" time=\"%s\">\n", session.getCourseid(), formattedTime));
//             for (AttendanceRecord record : session.getRecords()) {
//                 xmlBuilder.append("\t\t<attendanceRecord>\n");
//                 if (fields.contains("studentID")) {
//                     xmlBuilder.append(
//                             String.format("\t\t\t<studentID>%s</studentID>\n", record.getStudent().getUserid()));
//                 }
//                 if (fields.contains("legalName")) {
//                     xmlBuilder.append(
//                             String.format("\t\t\t<legalName>%s</legalName>\n", record.getStudent().getLegalName()));
//                 }
//                 if (fields.contains("displayName")) {
//                     xmlBuilder.append(String.format("\t\t\t<displayName>%s</displayName>\n",
//                             record.getStudent().getDisplayName()));
//                 }
//                 if (fields.contains("email")) {
//                     xmlBuilder.append(String.format("\t\t\t<email>%s</email>\n",
//                             record.getStudent().getEmail().getEmailAddr()));
//                 }
//                 if (fields.contains("status")) {
//                     xmlBuilder.append(String.format("\t\t\t<status>%s</status>\n", record.getStatus().getStatus()));
//                 }
//                 xmlBuilder.append("\t\t</attendanceRecord>\n");
//             }
//             xmlBuilder.append("\t</session>\n");
//         }
//         xmlBuilder.append("</sessions>");

//         try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//             writer.write(xmlBuilder.toString());
//         }
//     }
// }
