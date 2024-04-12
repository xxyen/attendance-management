package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.Section;
import edu.duke.ece651.shared.dao.SectionDAO;
import java.util.List;

/**
 * Service class that manages section operations.
 * This class provides a high-level interface for interacting with section-related data through the SectionDAO.
 * It handles operations such as adding, deleting, listing, and updating sections associated with courses.
 */
public class SectionService {

    private SectionDAO sectionDAO = new SectionDAO();

     /**
     * Adds a section to a specified course.
     * This method delegates the addition of a new section associated with a given course ID.
     * @param courseId the identifier of the course to which the section will be added.
     * @param section the section object containing the section details to be added.
     */
    public void addSectionToCourse(String courseId, Section section) {
        sectionDAO.addSectionToCourse(section);
    }

     /**
     * Deletes a section from the database.
     * This method removes a section identified by its section ID.
     * @param sectionId the identifier of the section to be deleted.
     */
    public void deleteSection(int sectionId) {
        sectionDAO.deleteSection(sectionId);
    }

    /**
     * Retrieves a list of sections associated with a specific course.
     * This method returns all sections linked to a particular course, identified by course ID.
     * @param courseId the identifier of the course whose sections are to be listed.
     * @return a list of section objects associated with the specified course.
     */
    public List<Section> listSectionsByCourse(String courseId) {
        return sectionDAO.listSectionsByCourse(courseId);
    }

     /**
     * Updates the professor associated with a specific section.
     * This method updates the faculty member teaching a section, identified by the section ID, to a new faculty ID.
     * @param sectionId the identifier of the section whose professor is to be updated.
     * @param newFacultyId the identifier of the new faculty member to be assigned to the section.
     */
    public void updateSectionProfessor(int sectionId, String newFacultyId) {
        sectionDAO.updateSectionProfessor(sectionId, newFacultyId);
    }
}
