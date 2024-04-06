package edu.duke.ece651.shared;

/**
 * A factory class for creating Exporter instances.
 */
public class ExporterFactory {
    /**
     * Creates and returns an Exporter instance based on the specified format.
     *
     * @param format the format of the exporter (e.g., "json", "xml", "custom")
     * @return an Exporter instance for the specified format
     * @throws IllegalArgumentException if the specified format is unsupported
     */
    public static Exporter getExporter(String format) {
        switch (format.toLowerCase()) {
            // case "json":
            //     return new JSONExporter();
            // case "xml":
            //     return new XMLExporter();
            case "custom":
                return new CustomExporter();
            default:
                throw new IllegalArgumentException("Unsupported file format: " + format);
        }
    }
}
