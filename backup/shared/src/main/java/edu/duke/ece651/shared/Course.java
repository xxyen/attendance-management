package edu.duke.ece651.shared;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a course with its details such as course ID, professors, students,
 * and sessions.
 * Allows manipulation of these details including adding and removing students
 * and professors.
 */
public class Course {
  private String courseid;
  private List<Professor> professors;
  private List<Student> students;
  private List<Session> sessions;
  private boolean canChangeName;

  /**
   * Creates a course instance allowing or disallowing the change of students'
   * display name.
   * 
   * @param canChangeName boolean indicating if the student's display name can be
   *                      changed.
   */
  public Course(boolean canChangeName) {
    professors = new ArrayList<>();
    students = new ArrayList<>();
    sessions = new ArrayList<>();
    this.canChangeName = canChangeName;
  }

  public Course(String courseid, Professor professor, boolean canChangeName) {
    this.courseid = courseid;
    professors = new ArrayList<>();
    professors.add(professor);
    students = new ArrayList<>();
    sessions = new ArrayList<>();
    this.canChangeName = canChangeName;
  }

  /**
   * Creates a course instance with specific course ID, list of professors,
   * students, and name change policy.
   * 
   * @param courseid        String representing the course ID.
   * @param professorsArray Array of Professor objects associated with the course.
   * @param studentsArray   Array of Student objects enrolled in the course.
   * @param canChangeName   boolean indicating if the course name can be changed.
   */
  public Course(String courseid, Professor[] professorsArray, Student[] studentsArray, boolean canChangeName) {
    this.courseid = courseid;
    this.professors = new ArrayList<>(Arrays.asList(professorsArray));
    this.students = new ArrayList<>(Arrays.asList(studentsArray));
    this.sessions = new ArrayList<>();
    this.canChangeName = canChangeName;
  }

  /**
   * Adds a new session to the course.
   * 
   * @param newSession Session object to be added to the course.
   */
  public void addSession(Session newSession) {
    this.sessions.add(newSession);
  }

  /**
   * Returns an iterator for the students enrolled in the course.
   * 
   * @return Iterator<Student> for accessing enrolled students.
   */
  public Iterator<Student> getStudent() {
    return this.students.iterator();
  }

  /**
   * Returns the list of students enrolled in the course.
   * 
   * @return List<Student> of enrolled students.
   */
  public List<Student> getStudents() {
    return students;
  }

  /**
   * Adds a new student to the course if the student is not already enrolled.
   * 
   * @param newStudent Student object to be added.
   * @throws IOException if there is an input or output exception.
   */
  public void addStudent(Student newStudent) throws IOException {
    if (!this.students.contains(newStudent)) {
      this.students.add(newStudent);
    }
  }

  /**
   * Returns the list of professors associated with the course.
   * 
   * @return List<Professor> of associated professors.
   */
  public List<Professor> getProfessors() {
    return professors;
  }

  /**
   * Adds a new professor to the course and associates the course ID with the
   * professor.
   * 
   * @param newProfessor Professor object to be added.
   */
  public void addProfessor(Professor newProfessor) {
    this.professors.add(newProfessor);
    newProfessor.addCourse(this.courseid);
  }

  /**
   * Removes a student from the course based on the student ID.
   * 
   * @param id String representing the student ID.
   * @throws IOException if there is an input or output exception.
   */
  public void removeStudent(String id) throws IOException {
    this.students.removeIf(student -> student.getPersonalID().equals(id));
  }

  /**
   * Returns the course ID.
   * 
   * @return String representing the course ID.
   */
  public String getCourseid() {
    return courseid;
  }

  /**
   * Returns the list of sessions associated with the course.
   * 
   * @return List<Session> of course sessions.
   */
  public List<Session> getSessions() {
    return sessions;
  }

  /**
   * Checks if the course name can be changed.
   * 
   * @return boolean indicating if the course name can be changed.
   */
  public boolean isCanChangeName() {
    return canChangeName;
  }

  /**
   * Sets the list of professors associated with the course.
   * 
   * @param professors List<Professor> to be associated with the course.
   */
  public void setProfessors(List<Professor> professors) {
    this.professors = professors;
  }

  /**
   * Sets the list of students enrolled in the course.
   * 
   * @param students List<Student> to be associated with the course.
   */
  public void setStudents(List<Student> students) {
    this.students = students;
  }

  /**
   * Sets the list of sessions associated with the course.
   * 
   * @param sessions List<Session> to be associated with the course.
   */
  public void setSessions(List<Session> sessions) {
    this.sessions = sessions;
  }
}
