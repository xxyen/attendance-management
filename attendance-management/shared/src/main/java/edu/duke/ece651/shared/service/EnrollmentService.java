package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.Enrollment;
import edu.duke.ece651.shared.dao.EnrollmentDAO;

public class EnrollmentService {

    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    public void registerStudentToSection(String studentId, int sectionId) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setSectionId(sectionId);
        enrollmentDAO.addEnrollment(enrollment);
    }

    public void unregisterStudentFromSection(String studentId, int sectionId) {
        int enrollmentId = enrollmentDAO.findEnrollmentByStudentAndSection(studentId, sectionId).getEnrollmentId();
        enrollmentDAO.deleteEnrollment(enrollmentId);
    }

    public void listStudentsBySection(int sectionId) {
        enrollmentDAO.listEnrollmentsBySection(sectionId);
    }
}
