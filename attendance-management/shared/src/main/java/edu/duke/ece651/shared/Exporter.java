package edu.duke.ece651.shared;

import java.io.IOException;
import java.util.List;

public interface Exporter {
    void export(List<Session> sessions, List<String> fields, String filePath) throws IOException;
}
