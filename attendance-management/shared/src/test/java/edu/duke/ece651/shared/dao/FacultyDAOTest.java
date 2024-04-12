package edu.duke.ece651.shared.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.Email;

public class FacultyDAOTest {
  FacultyDAO facultyDAO = new FacultyDAO();

  @Test
  public void test_all() {
    String userid = "notgoingtohapendhiudhushf";
    Professor faculty = new Professor(userid, userid, new Email("1ac@1ac.shd"));
    // if(facultyDAO.queryFacultyById(userid) != null) {
    // Professor originalFaculty = facultyDAO.queryFacultyById(userid);
    // facultyDAO.updateFaculty(originalFaculty);
    // } else {
    facultyDAO.queryFacultyById(userid);
    facultyDAO.queryFacultyByEmail("1ac@1ac.shd");
    facultyDAO.queryFacultyByLegalName(userid); 
    facultyDAO.addFaculty(faculty);
    facultyDAO.queryFacultyById(userid);
    facultyDAO.queryFacultyByEmail("1ac@1ac.shd");
    facultyDAO.queryFacultyByLegalName(userid);
    facultyDAO.queryFacultyByLegalName("adsfkh4w3875yrqeowxrnd2oq83x9oidfmcspitu4509tu09q2q3"); 
    facultyDAO.updateFaculty(faculty);
    facultyDAO.deleteFaculty(userid);
    facultyDAO.queryAllFaculty();
      //}
  }

}
