package edu.duke.ece651.shared;

import java.util.ArrayList;

public class Professor {
    private String name;
    private String professorID;
    private Email email;
    private ArrayList<String> courseids;

    public Professor(String name, String professorID, Email email, ArrayList<String> courseids) {
        this.name = name;
        this.professorID = professorID;
        this.email = email;
        this.courseids = courseids;
    }

    public Professor(String name, String professorID, Email email) {
        this(name, professorID, email, new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public String getProfessorID() {
        return professorID;
    }

    public Email getEmail() {
        return email;
    }

    public ArrayList<String> getCourseids() {
        return courseids;
    }

    public boolean addCourse(String courseid) {
        if (courseids.contains(courseid)) {
            return false;
        }
        courseids.add(courseid);
        return true;
    }

    public boolean removeCourse(String courseid) {
        if (courseids.contains(courseid)) {
            courseids.remove(courseid);
        }
        return false;
    }
}
