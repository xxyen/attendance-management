package edu.duke.ece651.shared.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.*;
import java.util.List;


public class SectionDAOTest {
  @Test
    public void test_section() throws Exception {
      CourseDAO courseDAO = new CourseDAO();
      SectionDAO sectionDAO = new SectionDAO();
      FacultyDAO facultyDAO = new FacultyDAO();
      Course newCourse = new Course("CS102", "Introduction to CS");
      int result = courseDAO.addCourse(newCourse);

      Professor prof1 = new Professor("F001", "f01", new Email("f1@duke.edu"));
      Professor prof2 = new Professor("F002", "f02", new Email("f2@duke.edu"));
      facultyDAO.addFaculty(prof1);
      facultyDAO.addFaculty(prof2);

      Section newSection = new Section("CS102", "F001");
      int addResult = sectionDAO.addSectionToCourse(newSection);
      // assertEquals(1, addResult);

      List<Section> sections = sectionDAO.listSectionsByCourse("CS102");
      System.out.println(sections);
      assertFalse(sections.isEmpty());
      Section queriedSection = sections.get(0);
      assertEquals("F001", queriedSection.getFacultyId());
      sectionDAO.updateSection(queriedSection);
      
      int updateResult = sectionDAO.updateSectionProfessor(queriedSection.getSectionId(), "F002");
      assertEquals(1, updateResult);
      Section updatedSection = sectionDAO.querySectionById(queriedSection.getSectionId());
      assertEquals("F002", updatedSection.getFacultyId());

      List<Section> queriedSection2 = sectionDAO.queryAllSections();
      System.out.println(queriedSection2);
      assertNotNull(queriedSection2);

      List<Section> queriedSection3 = sectionDAO.querySectionByFaculty("F002");
      assertNotNull(queriedSection3);

      int deleteResult = sectionDAO.deleteSection(queriedSection.getSectionId());
      assertEquals(1, deleteResult);
      sections = sectionDAO.listSectionsByCourse("CS102");
      assertTrue(sections.isEmpty());

      courseDAO.deleteCourse("CS102");
      facultyDAO.deleteFaculty("F001");
      facultyDAO.deleteFaculty("F002");
    }
}
