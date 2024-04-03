package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.JDBCUtils;
import edu.duke.ece651.shared.model.Enrollment;
import java.sql.SQLException;
import java.util.List;

public class EnrollmentDAO extends BasicDAO<Enrollment> {

    public int addEnrollment(Enrollment enrollment) {
        String sql = "INSERT INTO enrollment (section_id, student_id, enrollment_date, status) VALUES (?, ?, ?, ?)";
        return update(sql, enrollment.getSectionId(), enrollment.getStudentId(), new java.sql.Date(enrollment.getEnrollmentDate().getTime()), enrollment.getStatus());
    }

    public int deleteEnrollment(Integer enrollmentId) {
        String sql = "DELETE FROM enrollment WHERE enrollment_id = ?";
        return update(sql, enrollmentId);
    }

    public int updateEnrollment(Enrollment enrollment) {
        String sql = "UPDATE enrollment SET section_id = ?, student_id = ?, enrollment_date = ?, status = ? WHERE enrollment_id = ?";
        return update(sql, enrollment.getSectionId(), enrollment.getStudentId(), new java.sql.Date(enrollment.getEnrollmentDate().getTime()), enrollment.getStatus(), enrollment.getEnrollmentId());
    }

    public Enrollment queryEnrollmentById(Integer enrollmentId) {
        String sql = "SELECT * FROM enrollment WHERE enrollment_id = ?";
        return querySingle(sql, Enrollment.class, enrollmentId);
    }

    public List<Enrollment> queryAllEnrollments() {
        String sql = "SELECT * FROM enrollment";
        return queryMulti(sql, Enrollment.class);
    }
}
