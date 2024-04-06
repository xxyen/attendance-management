package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.dao.*;
import java.util.List;
import java.util.ArrayList;

public class EnrollmentService {

    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private StudentDAO studentDAO = new StudentDAO();

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

    public List<Student> listStudentsBySection(int sectionId) {
        List<Enrollment> enrollments = enrollmentDAO.listEnrollmentsBySection(sectionId);
        List<Student> students = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            Student student = studentDAO.queryStudentById(enrollment.getStudentId());
            if (student != null) {
                students.add(student);
            }
        }
        return students;
    }
}
