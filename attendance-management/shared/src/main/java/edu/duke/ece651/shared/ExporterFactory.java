package edu.duke.ece651.shared;

public class ExporterFactory {
    public static Exporter getExporter(String format) {
        switch (format.toLowerCase()) {
            case "json":
                return new JSONExporter();
            case "xml":
                return new XMLExporter();
            case "custom":
                return new CustomExporter();
            default:
                throw new IllegalArgumentException("Unsupported file format: " + format);
        }
    }
}
