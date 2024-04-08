package edu.duke.ece651.shared.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.*;
import java.util.List;
import java.util.Date;
import java.sql.Time;

public class SessionDAOTest {
  @Test
    public void test_session() throws Exception {
      CourseDAO courseDAO = new CourseDAO();
      SectionDAO sectionDAO = new SectionDAO();
      FacultyDAO facultyDAO = new FacultyDAO();
      SessionDAO sessionDAO = new SessionDAO();
      Course newCourse = new Course("CS103", "Introduction to CS");
      int result = courseDAO.addCourse(newCourse);

      Professor prof1 = new Professor("F004", "f04", new Email("f4@duke.edu"));
      facultyDAO.addFaculty(prof1);

      Section newSection = new Section("CS103", "F004");
      sectionDAO.addSectionToCourse(newSection);

      Session newSession = new Session(newSection.getSectionId(), new Date(), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"));
      System.out.println(newSession);
      int addResult = sessionDAO.addSession(newSession);
      assertEquals(1, addResult);

      List<Session> sessions = sessionDAO.listSessionsBySection(newSection.getSectionId());
      assertTrue(!sessions.isEmpty());
      Session queriedSession = sessions.get(0);
      assertEquals(Time.valueOf("10:00:00").toString(), queriedSession.getStartTime().toString());

      queriedSession.setEndTime(Time.valueOf("13:00:00"));
      int updateResult = sessionDAO.updateSession(queriedSession);
      assertEquals(1, updateResult);
      Session updatedSession = sessionDAO.findSessionById(queriedSession.getSessionId());
      assertEquals("13:00:00", updatedSession.getEndTime().toString());

      List<Session> allSessions = sessionDAO.findAllSessions();
      assertNotNull(allSessions);

      int deleteResult = sessionDAO.deleteSession(queriedSession.getSessionId());
      assertEquals(1, deleteResult);

      sessions = sessionDAO.listSessionsBySection(newSection.getSectionId());
      assertTrue(sessions.isEmpty());


      sectionDAO.deleteSection(newSection.getSectionId());
      courseDAO.deleteCourse("CS103");
      facultyDAO.deleteFaculty("F004");
    }

}
