package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.Section;
import edu.duke.ece651.shared.dao.SectionDAO;
import java.util.List;


public class SectionService {

    private SectionDAO sectionDAO = new SectionDAO();

    public void addSectionToCourse(String courseId, Section section) {
        section.setCourseId(courseId);
        sectionDAO.addSectionToCourse(section);
    }

    public void deleteSection(int sectionId) {
        sectionDAO.deleteSection(sectionId);
    }

    public List<Section> listSectionsByCourse(String courseId) {
        return sectionDAO.listSectionsByCourse(courseId);
    }

    public void updateSectionProfessor(int sectionId, String newFacultyId) {
        sectionDAO.updateSectionProfessor(sectionId, newFacultyId);
    }
}
