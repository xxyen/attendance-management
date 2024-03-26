package edu.duke.ece651.shared;

import java.io.IOException;
import java.util.List;

/**
 * Defines the interface for exporting session data into different file formats.
 */
public interface Exporter {
    /**
     * Exports a list of sessions to a specified file path in a specific format.
     *
     * @param sessions the list of sessions to be exported
     * @param fields   the list of fields to be included in the export
     * @param filePath the file path where the exported data will be saved
     * @throws IOException if an I/O error occurs writing to or creating the file
     */
    void export(List<Session> sessions, List<String> fields, String filePath) throws IOException;
}
