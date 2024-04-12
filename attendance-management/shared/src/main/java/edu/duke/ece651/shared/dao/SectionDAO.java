package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.JDBCUtils;
import edu.duke.ece651.shared.model.Section;
import java.sql.SQLException;
import java.util.List;

/**
 * The SectionDAO class provides data access operations specific to sections.
 * It extends the BasicDAO class and inherits basic data access methods.
 */
public class SectionDAO extends BasicDAO<Section> {
    /**
     * Adds a new section to a course in the database.
     *
     * @param section the Section object representing the section to be added
     * @return the ID of the newly added section
     */
    public int addSectionToCourse(Section section) {
        String sql = "INSERT INTO section (course_id, faculty_id) VALUES (?, ?)";
        long generatedId = insertAndGetGeneratedKey(sql, section.getCourseId(), section.getFacultyId());
        section.setSectionId((int) generatedId); 
        return (int)generatedId;
        // return section;
        // return update(sql, section.getCourseId(), section.getFacultyId());
    }

    /**
     * Deletes a section from the database based on its ID.
     *
     * @param sectionId the ID of the section to be deleted
     * @return the number of rows affected by the delete operation
     */
    public int deleteSection(int sectionId) {
        String sql = "DELETE FROM section WHERE section_id = ?";
        return update(sql, sectionId);
    }

    /**
     * Updates information for an existing section in the database.
     *
     * @param section the Section object representing the updated information
     * @return the number of rows affected by the update operation
     */
    public int updateSection(Section section) {
        String sql = "UPDATE section SET course_id = ?, faculty_id = ? WHERE section_id = ?";
        return update(sql, section.getCourseId(), section.getFacultyId(), section.getSectionId());
    }

    /**
     * Retrieves information for a section based on its ID.
     *
     * @param sectionId the ID of the section to query
     * @return a Section object representing the queried section, or null if not found
     */
    public Section querySectionById(int sectionId) {
        String sql = "SELECT section_id AS sectionId, course_id AS courseId, faculty_id AS facultyId FROM section WHERE section_id = ?";
        return querySingle(sql, Section.class, sectionId);
    }

    /**
     * Retrieves information for all sections in the database.
     *
     * @return a list of Section objects representing all sections
     */
    public List<Section> queryAllSections() {
        String sql = "SELECT section_id AS sectionId, course_id AS courseId, faculty_id AS facultyId FROM section";
        return queryMulti(sql, Section.class);
    }


    /**
     * Updates the professor for a section in the database.
     *
     * @param sectionId the ID of the section to update
     * @param newFacultyId the ID of the new professor
     * @return the number of rows affected by the update operation
     */
    public int updateSectionProfessor(int sectionId, String newFacultyId) {
        String sql = "UPDATE section SET faculty_id = ? WHERE section_id = ?";
        return update(sql, newFacultyId, sectionId);
    }

    /**
     * Retrieves a list of sections for a given course.
     *
     * @param courseId the ID of the course to query
     * @return a list of Section objects representing the sections for the course
     */
    public List<Section> listSectionsByCourse(String courseId) {
        String sql = "SELECT section_id AS sectionId, course_id AS courseId, faculty_id AS facultyId FROM section WHERE course_id = ?";
        return queryMulti(sql, Section.class, courseId);
    }

    /**
     * Retrieves a list of sections taught by a specific faculty member.
     *
     * @param facultyId the ID of the faculty member to query
     * @return a list of Section objects representing the sections taught by the faculty member
     */
    public List<Section> querySectionByFaculty(String facultyId) {
        String sql = "SELECT section_id AS sectionId, course_id AS courseId, faculty_id AS facultyId FROM section WHERE faculty_id = ?";
        return queryMulti(sql, Section.class, facultyId);
    }

    /**
     * Retrieves a list of sections in which a given student is enrolled.
     *
     * @param studentId the ID of the student to query
     * @return a list of Section objects representing the sections in which the student is enrolled
     */
    public List<Section> listSectionsByStudentId(String studentId) {
        String sql = "SELECT s.section_id AS sectionId, s.course_id AS courseId, s.faculty_id AS facultyId " +
                     "FROM section s JOIN enrollment e ON s.section_id = e.section_id " +
                     "WHERE e.student_id = ?";
        return queryMulti(sql, Section.class, studentId);
    }
    
}
