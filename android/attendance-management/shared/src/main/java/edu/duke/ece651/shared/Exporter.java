package edu.duke.ece651.shared;

import java.io.IOException;
import java.util.List;
import edu.duke.ece651.shared.model.*;


/**
 * Defines the interface for exporting session data into different file formats.
 */
public interface Exporter {
    void exportAttendanceDataForProfessor(int sectionId, String filePath) throws IOException;
    void exportAttendanceDataForStudent(String studentId, int sectionId, String filePath) throws IOException;
}
