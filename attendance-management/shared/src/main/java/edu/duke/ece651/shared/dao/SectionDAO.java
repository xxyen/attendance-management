package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.JDBCUtils;
import edu.duke.ece651.shared.Section;
import java.sql.SQLException;
import java.util.List;

public class SectionDAO extends BasicDAO<Section> {
    
    public int addSection(Section section) {
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
        String sql = "SELECT * FROM section WHERE section_id = ?";
        return querySingle(sql, Section.class, sectionId);
    }

    public List<Section> queryAllSections() {
        String sql = "SELECT * FROM section";
        return queryMulti(sql, Section.class);
    }
}
