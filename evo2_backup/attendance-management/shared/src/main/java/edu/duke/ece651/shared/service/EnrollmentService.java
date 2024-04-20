package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.dao.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Service class for managing student enrollments in sections.
 * This class provides methods to register and unregister students from sections, list students by section,
 * and manage notification preferences for students in their respective sections.
 */
public class EnrollmentService {

    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private StudentDAO studentDAO = new StudentDAO();

    /**
     * Registers a student to a section by creating a new enrollment.
     * @param studentId the ID of the student to register.
     * @param sectionId the ID of the section to which the student will be registered.
     */
    public void registerStudentToSection(String studentId, int sectionId) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setSectionId(sectionId);
        enrollmentDAO.addEnrollment(enrollment);
    }

    /**
     * Unregisters a student from a section by deleting the corresponding enrollment.
     * @param studentId the ID of the student to unregister.
     * @param sectionId the ID of the section from which the student will be unregistered.
     */
    public void unregisterStudentFromSection(String studentId, int sectionId) {
        int enrollmentId = enrollmentDAO.findEnrollmentByStudentAndSection(studentId, sectionId).getEnrollmentId();
        enrollmentDAO.deleteEnrollment(enrollmentId);
    }

     /**
     * Lists all students enrolled in a specific section.
     * @param sectionId the ID of the section for which the student list is requested.
     * @return a list of students enrolled in the specified section.
     */
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

    /**
     * Checks if a student is set to receive notifications for a section.
     * @param studentId the ID of the student.
     * @param sectionId the ID of the section.
     * @return true if the student is set to receive notifications, false otherwise.
     * @throws IllegalArgumentException if no enrollment is found for the given student and section IDs.
     */
    public boolean doesStudentReceiveNotifications(String studentId, int sectionId) {
        Enrollment enrollment = enrollmentDAO.findEnrollmentByStudentAndSection(studentId, sectionId);
        if (enrollment != null) {
            return enrollment.isReceiveNotifications();
        } else {
            throw new IllegalArgumentException("No enrollment found for the given student and section IDs.");
        }
    }

    /**
     * Updates a student's preference for receiving notifications for a section.
     * @param studentId the ID of the student whose preference is to be updated.
     * @param sectionId the ID of the section relevant to the notification preference.
     * @param receiveNotifications true to enable notifications, false to disable them.
     * @throws IllegalArgumentException if no enrollment is found for the given student and section IDs to update.
     */
    public void updateStudentNotificationPreference(String studentId, int sectionId, boolean receiveNotifications) {
        Enrollment enrollment = enrollmentDAO.findEnrollmentByStudentAndSection(studentId, sectionId);
        if (enrollment != null) {
            enrollment.setReceiveNotifications(receiveNotifications);
            enrollmentDAO.updateEnrollment(enrollment);
        } else {
            throw new IllegalArgumentException("No enrollment found for the given student and section IDs to update.");
        }
    }
    
    
}
