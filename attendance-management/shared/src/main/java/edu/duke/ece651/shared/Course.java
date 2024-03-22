package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Course {
  private String courseid;
  private List<Professor> professors;
  private List<Student> students;
  private List<Session> sessions;
  private boolean canChangeName;

  public Course(boolean canChangeName) {
    professors = new ArrayList<>();
    students = new ArrayList<>();
    sessions = new ArrayList<>();
    this.canChangeName = canChangeName;
  }

  public Course(String courseid, Professor[] professorsArray, Student[] studentsArray, boolean canChangeName) {
    this.courseid = courseid;
    this.professors = new ArrayList<>(Arrays.asList(professorsArray));
    this.students = new ArrayList<>(Arrays.asList(studentsArray));
    this.sessions = new ArrayList<>();
    this.canChangeName = canChangeName;
  }
  // public Course(String courseid, Student[] studentsArray, boolean
  // canChangeName) {
  // this.courseid = courseid;
  // this.students = new ArrayList<>(Arrays.asList(studentsArray));
  // this.sessions = new ArrayList<>();
  // this.canChangeName = canChangeName;
  // }

  public void addSession(Session newSession) {
    this.sessions.add(newSession);
  }

  public Iterator<Student> getStudent() {
    return this.students.iterator();
  }

  public List<Student> getStudents() {
    return students;
  }

  public void addStudent(Student newStudent) {
    this.students.add(newStudent);
  }

  public List<Professor> getProfessors() {
    return professors;
  }

  public void addProfessor(Professor newProfessor) {
    this.professors.add(newProfessor);
  }

  public void removeStudent(String id) {
    this.students.removeIf(student -> student.getStudentID().equals(id));
  }

  public String getCourseid() {
    return courseid;
  }

  // public List<Professor> getProfessors() {
  // return professors;
  // }

  public List<Session> getSessions() {
    return sessions;
  }

  public boolean isCanChangeName() {
    return canChangeName;
  }

  public void setProfessors(List<Professor> professors) {
    this.professors = professors;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  public void setSessions(List<Session> sessions) {
    this.sessions = sessions;
  }
}
