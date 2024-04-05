package edu.duke.ece651.shared.model;

public class Section {
    private int sectionId;
    private String courseId;
    private String facultyId;

    public Section() {}

    public Section(int sectionId, String courseId, String facultyId) {
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.facultyId = facultyId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }
}
