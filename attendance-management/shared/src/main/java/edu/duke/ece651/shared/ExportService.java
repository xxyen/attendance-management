package edu.duke.ece651.shared;

import java.io.IOException;
import java.util.List;
import edu.duke.ece651.shared.model.*;


/**
 * A service class for exporting session data into files.
 */
public class ExportService {
    /**
     * Exports session data to a file in the specified format.
     *
     * @param sessions the sessions to export
     * @param format   the format to export the sessions in
     * @param fields   the fields to include in the export
     * @param filePath the path to save the exported file
     * @throws IOException if an error occurs during the export process
     */
    public static void exportToFile(List<Session> sessions, String format, List<String> fields, String filePath)
            throws IOException {
        Exporter exporter = ExporterFactory.getExporter(format);
        exporter.export(sessions, fields, filePath);
    }
}
