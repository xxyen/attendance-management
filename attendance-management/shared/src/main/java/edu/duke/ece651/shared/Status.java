package edu.duke.ece651.shared;

public class Status {
    private char status;

    public Status(char status) {
        this.setStatus(status);
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        if (status == 'p' || status == 'a' || status == 'l' || status == 'n') {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}
