package edu.duke.ece651.shared;

public class Student implements User {
    private String studentID;

    private String legalName;

    private String displayName;

    private Email emailAddr;

    public Student(String studentID, String legalName, String displayName, Email emailAddr) {
        this.studentID = studentID;
        this.legalName = legalName;
        this.displayName = displayName;
        this.emailAddr = emailAddr;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmailAddr(Email emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getPersonalID() {
        return studentID;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Email getEmailAddr() {
        return emailAddr;
    }
}
