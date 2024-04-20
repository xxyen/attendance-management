package edu.duke.ece651.shared;

import java.io.IOException;
import java.util.List;
import edu.duke.ece651.shared.model.*;


/**
 * A custom implementation of the Exporter interface.
 */
public class CustomExporter implements Exporter {
    @Override
    public void exportAttendanceDataForProfessor(int sectionId, String filePath) throws IOException {
        // custom export format
    }

    @Override
    public void exportAttendanceDataForStudent(String studentId, int sectionId, String filePath) throws IOException {
    }
}
