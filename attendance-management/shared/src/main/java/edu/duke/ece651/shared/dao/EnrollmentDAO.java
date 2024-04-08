package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.JDBCUtils;
import edu.duke.ece651.shared.model.Enrollment;
import java.sql.SQLException;
import java.util.List;

public class EnrollmentDAO extends BasicDAO<Enrollment> {

    public int addEnrollment(Enrollment enrollment) {
        String sql = "INSERT INTO enrollment (section_id, student_id, enrollment_date, status, receive_notifications) VALUES (?, ?, ?, ?, ?)";
        long generatedId = insertAndGetGeneratedKey(sql, enrollment.getSectionId(), enrollment.getStudentId(), new java.sql.Date(enrollment.getEnrollmentDate().getTime()), enrollment.getStatus(), enrollment.isReceiveNotifications());
        enrollment.setEnrollmentId((int) generatedId);
        return (int)generatedId;
        // return update(sql, enrollment.getSectionId(), enrollment.getStudentId(), new java.sql.Date(enrollment.getEnrollmentDate().getTime()), enrollment.getStatus(), enrollment.isReceiveNotifications());
    }

    public int deleteEnrollment(Integer enrollmentId) {
        String sql = "DELETE FROM enrollment WHERE enrollment_id = ?";
        return update(sql, enrollmentId);
    }

    public int updateEnrollment(Enrollment enrollment) {
        String sql = "UPDATE enrollment SET section_id = ?, student_id = ?, enrollment_date = ?, status = ?, receive_notifications = ? WHERE enrollment_id = ?";
        return update(sql, enrollment.getSectionId(), enrollment.getStudentId(), new java.sql.Date(enrollment.getEnrollmentDate().getTime()), enrollment.getStatus(), enrollment.isReceiveNotifications(), enrollment.getEnrollmentId());
    }

    public Enrollment queryEnrollmentById(Integer enrollmentId) {
        String sql = "SELECT enrollment_id AS enrollmentId, section_id AS sectionId, student_id AS studentId, enrollment_date AS enrollmentDate, status AS status, receive_notifications AS receiveNotifications FROM enrollment WHERE enrollment_id = ?";
        return querySingle(sql, Enrollment.class, enrollmentId);
    }

    public List<Enrollment> queryAllEnrollments() {
        String sql = "SELECT enrollment_id AS enrollmentId, section_id AS sectionId, student_id AS studentId, enrollment_date AS enrollmentDate, status AS status, receive_notifications AS receiveNotifications FROM enrollment";
        return queryMulti(sql, Enrollment.class);
    }

    public Enrollment findEnrollmentByStudentAndSection(String studentId, int sectionId) {
        String sql = "SELECT enrollment_id AS enrollmentId, section_id AS sectionId, student_id AS studentId, enrollment_date AS enrollmentDate, status AS status, receive_notifications AS receiveNotifications FROM enrollment WHERE student_id = ? AND section_id = ?";
        return querySingle(sql,Enrollment.class, studentId, sectionId);
    }

    public List<Enrollment> listEnrollmentsBySection(int section_id) {
        String sql = "SELECT enrollment_id AS enrollmentId, section_id AS sectionId, student_id AS studentId, enrollment_date AS enrollmentDate, status AS status, receive_notifications AS receiveNotifications FROM enrollment WHERE section_id = ?";
        return queryMulti(sql, Enrollment.class, section_id);
    }
}
