package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.JDBCUtils;
import edu.duke.ece651.shared.model.Enrollment;
import java.sql.SQLException;
import java.util.List;

/**
 * The EnrollmentDAO class provides data access operations specific to enrollments.
 * It extends the BasicDAO class and inherits basic data access methods.
 */
public class EnrollmentDAO extends BasicDAO<Enrollment> {

    /**
     * Adds a new enrollment to the database.
     *
     * @param enrollment the Enrollment object representing the enrollment to be added
     * @return the ID of the newly added enrollment
     */
    public int addEnrollment(Enrollment enrollment) {
        String sql = "INSERT INTO enrollment (section_id, student_id, enrollment_date, status, receive_notifications) VALUES (?, ?, ?, ?, ?)";
        long generatedId = insertAndGetGeneratedKey(sql, enrollment.getSectionId(), enrollment.getStudentId(), new java.sql.Date(enrollment.getEnrollmentDate().getTime()), enrollment.getStatus(), enrollment.isReceiveNotifications());
        enrollment.setEnrollmentId((int) generatedId);
        return (int)generatedId;
        // return update(sql, enrollment.getSectionId(), enrollment.getStudentId(), new java.sql.Date(enrollment.getEnrollmentDate().getTime()), enrollment.getStatus(), enrollment.isReceiveNotifications());
    }

    /**
     * Deletes an enrollment from the database based on its ID.
     *
     * @param enrollmentId the ID of the enrollment to be deleted
     * @return the number of rows affected by the delete operation
     */
    public int deleteEnrollment(Integer enrollmentId) {
        String sql = "DELETE FROM enrollment WHERE enrollment_id = ?";
        return update(sql, enrollmentId);
    }

    /**
     * Updates information for an existing enrollment in the database.
     *
     * @param enrollment the Enrollment object representing the updated information
     * @return the number of rows affected by the update operation
     */
    public int updateEnrollment(Enrollment enrollment) {
        String sql = "UPDATE enrollment SET section_id = ?, student_id = ?, enrollment_date = ?, status = ?, receive_notifications = ? WHERE enrollment_id = ?";
        return update(sql, enrollment.getSectionId(), enrollment.getStudentId(), new java.sql.Date(enrollment.getEnrollmentDate().getTime()), enrollment.getStatus(), enrollment.isReceiveNotifications(), enrollment.getEnrollmentId());
    }

    /**
     * Retrieves information for an enrollment based on its ID.
     *
     * @param enrollmentId the ID of the enrollment to query
     * @return an Enrollment object representing the queried enrollment, or null if not found
     */
    public Enrollment queryEnrollmentById(Integer enrollmentId) {
        String sql = "SELECT enrollment_id AS enrollmentId, section_id AS sectionId, student_id AS studentId, enrollment_date AS enrollmentDate, status AS status, receive_notifications AS receiveNotifications FROM enrollment WHERE enrollment_id = ?";
        return querySingle(sql, Enrollment.class, enrollmentId);
    }

    /**
     * Retrieves information for all enrollments in the database.
     *
     * @return a list of Enrollment objects representing all enrollments
     */
    public List<Enrollment> queryAllEnrollments() {
        String sql = "SELECT enrollment_id AS enrollmentId, section_id AS sectionId, student_id AS studentId, enrollment_date AS enrollmentDate, status AS status, receive_notifications AS receiveNotifications FROM enrollment";
        return queryMulti(sql, Enrollment.class);
    }

    /**
     * Finds an enrollment by student ID and section ID.
     *
     * @param studentId the ID of the student
     * @param sectionId the ID of the section
     * @return an Enrollment object representing the found enrollment, or null if not found
     */
    public Enrollment findEnrollmentByStudentAndSection(String studentId, int sectionId) {
        String sql = "SELECT enrollment_id AS enrollmentId, section_id AS sectionId, student_id AS studentId, enrollment_date AS enrollmentDate, status AS status, receive_notifications AS receiveNotifications FROM enrollment WHERE student_id = ? AND section_id = ?";
        return querySingle(sql,Enrollment.class, studentId, sectionId);
    }

    /**
     * Lists enrollments for a given section.
     *
     * @param sectionId the ID of the section
     * @return a list of Enrollment objects representing the enrollments for the section
     */
    public List<Enrollment> listEnrollmentsBySection(int section_id) {
        String sql = "SELECT enrollment_id AS enrollmentId, section_id AS sectionId, student_id AS studentId, enrollment_date AS enrollmentDate, status AS status, receive_notifications AS receiveNotifications FROM enrollment WHERE section_id = ?";
        return queryMulti(sql, Enrollment.class, section_id);
    }
}
