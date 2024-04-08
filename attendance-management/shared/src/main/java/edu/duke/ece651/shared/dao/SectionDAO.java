package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.JDBCUtils;
import edu.duke.ece651.shared.model.Section;
import java.sql.SQLException;
import java.util.List;

public class SectionDAO extends BasicDAO<Section> {
    
    public int addSectionToCourse(Section section) {
        String sql = "INSERT INTO section (course_id, faculty_id) VALUES (?, ?)";
        return update(sql, section.getCourseId(), section.getFacultyId());
    }

    public int deleteSection(int sectionId) {
        String sql = "DELETE FROM section WHERE section_id = ?";
        return update(sql, sectionId);
    }

    public int updateSection(Section section) {
        String sql = "UPDATE section SET course_id = ?, faculty_id = ? WHERE section_id = ?";
        return update(sql, section.getCourseId(), section.getFacultyId(), section.getSectionId());
    }

    public Section querySectionById(int sectionId) {
        String sql = "SELECT section_id AS sectionId, course_id AS courseId, faculty_id AS facultyId FROM section WHERE section_id = ?";
        return querySingle(sql, Section.class, sectionId);
    }

    public List<Section> queryAllSections() {
        String sql = "SELECT section_id AS sectionId, course_id AS courseId, faculty_id AS facultyId FROM section";
        return queryMulti(sql, Section.class);
    }


    public int updateSectionProfessor(int sectionId, String newFacultyId) {
        String sql = "UPDATE section SET faculty_id = ? WHERE section_id = ?";
        return update(sql, newFacultyId, sectionId);
    }

    public List<Section> listSectionsByCourse(String courseId) {
        String sql = "SELECT section_id AS sectionId, course_id AS courseId, faculty_id AS facultyId FROM section WHERE course_id = ?";
        return queryMulti(sql, Section.class, courseId);
    }

    public List<Section> querySectionByFaculty(int facultyId) {
        String sql = "SELECT section_id AS sectionId, course_id AS courseId, faculty_id AS facultyId FROM section WHERE faculty_id = ?";
        return queryMulti(sql, Section.class, facultyId);
    }
    
}
