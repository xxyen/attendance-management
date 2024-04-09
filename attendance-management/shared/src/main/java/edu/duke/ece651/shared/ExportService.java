package edu.duke.ece651.shared;

import java.io.IOException;
import java.util.List;
import edu.duke.ece651.shared.model.*;


/**
 * A service class for exporting session data into files.
 */
public class ExportService {
    public static void exportSectionAttendanceData(int sectionId, String format, String filePath) throws IOException {
        Exporter exporter = ExporterFactory.getExporter(format);
        exporter.exportAttendanceData(sectionId, filePath);
    }
}
