package edu.duke.ece651.shared;

import java.io.IOException;
import java.util.List;

/**
 * A custom implementation of the Exporter interface.
 */
public class CustomExporter implements Exporter {
    @Override
    public void export(List<Session> sessions, List<String> fields, String filePath) throws IOException {
        // custom export format
    }
}
