package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.User;
import edu.duke.ece651.shared.dao.FacultyDAO;
import edu.duke.ece651.shared.dao.StudentDAO;

public class UserOperator {
  private FacultyDAO facultyDAO = new FacultyDAO();
  private StudentDAO studentDAO = new StudentDAO();

  public User signIn(String userid, String password) {
    Professor faculty = facultyDAO.queryFacultyById(userid);
    if((faculty != null) && faculty.isCorrectPassword(password)) {
      return faculty;
    }
    Student student = studentDAO.queryStudentById(userid);
    if((student != null) && student.isCorrectPassword(password)) {
      return student;
    }

    System.out.println("test d");

    throw new IllegalArgumentException("The userid or password is invalid!");
  }

}
