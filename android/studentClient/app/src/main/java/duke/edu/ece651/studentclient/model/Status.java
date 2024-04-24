package duke.edu.ece651.studentclient.model;
/**
 * Represents the attendance status of a student.
 */
public class Status {
    private char status;

    /**
     * Constructs a Status with a specified character indicating the student's
     * attendance status.
     *
     * @param status the character representing the status. Valid characters are 'p'
     *               (present), 'a' (absent),
     *               'l' (late), or 'n' (not enrolled).
     */
    public Status(char status) {
        this.setStatus(status);
    }

    /**
     * Returns the current status character.
     *
     * @return the status character
     */
    public char getStatus() {
        return status;
    }

    /**
     * Sets the status character if it is valid.
     *
     * @param status the new status character to be set. Must be 'p', 'a' or 't'
     * @throws IllegalArgumentException if the status character is not valid.
     */
    public void setStatus(char status) {
        if (status == 'p' || status == 'a' || status == 't') {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}
