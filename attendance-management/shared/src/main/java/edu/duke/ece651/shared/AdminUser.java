package edu.duke.ece651.shared;

import edu.duke.ece651.shared.dao.FacultyDAO;
import edu.duke.ece651.shared.dao.StudentDAO;

public class AdminUser implements User {
    private static final AdminUser INSTANCE = new AdminUser();
    private String userid = "admin";
    private String password = "SthYouDonotKnow77890";
    private FacultyDAO facultyDAO = new FacultyDAO();
    private StudentDAO studentDAO = new StudentDAO();

    private AdminUser() {}

    public static AdminUser getInstance() {
        return INSTANCE;
    }

    @Override
    public String getUserid() {
        return userid;
    }

    @Override
    public String getUserType() {
        return "administrator";
    }

    @Override
    public boolean isCorrectPassword(String pwd) {
        return pwd.equals(password);
    }

    @Override
    public String getPassword() {   
        return password;
    }

    @Override
    public Email getEmail(){
        return null;
    }

    public boolean facultySignUp(Professor faculty){
        if(facultyDAO.queryFacultyById(faculty.getUserid()) == null) {
            return false;
        }
        else {
            facultyDAO.addFaculty(faculty);
            return true;
        }
      }
    
      public boolean studentSignUp(Student student){
        if(studentDAO.queryStudentById(student.getUserid()) == null) {
            return false;
        }
        else {
            studentDAO.addStudent(student);
            return true;
        }
      }
    
      public void removeFaculty(String userid) {
        facultyDAO.deleteFaculty(userid);
      }
    
      public void removeStudent(String userid) {
        studentDAO.deleteStudent(userid);
      }
    
      public void updateFaculty(Professor faculty) {
        facultyDAO.updateFaculty(faculty);
      }
    
      public void updateStudent(Student student){
        studentDAO.updateStudent(student);
      }
}
