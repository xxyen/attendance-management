package edu.duke.ece651.server;

import edu.duke.ece651.shared.AdminUser;
import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.User;
import edu.duke.ece651.shared.dao.FacultyDAO;
import edu.duke.ece651.shared.dao.StudentDAO;

public class UserOperator {
  private FacultyDAO facultyDAO = new FacultyDAO();
  private StudentDAO studentDAO = new StudentDAO();
  AdminUser admin = AdminUser.getInstance();

  public User signIn(String userid, String password) {
    if(userid.equals(admin.getUserid()) && admin.isCorrectPassword(password)) {
      return admin;
    } else {
      User user = facultyDAO.queryFacultyById(userid);
      if((user != null) && user.isCorrectPassword(password)) {
        return user;
      }
      user = studentDAO.queryStudentById(userid);
      if((user != null) && password.equals(user.getPassword())) {
        return user;
      }
      throw new IllegalArgumentException("The userid or password is invalid!");
    }
  }

}
