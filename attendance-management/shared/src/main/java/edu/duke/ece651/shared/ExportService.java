package edu.duke.ece651.shared;

import java.io.IOException;
import java.util.List;

public class ExportService {
    public static void exportToFile(List<Session> sessions, String format, List<String> fields, String filePath)
            throws IOException {
        Exporter exporter = ExporterFactory.getExporter(format);
        exporter.export(sessions, fields, filePath);
    }
}
