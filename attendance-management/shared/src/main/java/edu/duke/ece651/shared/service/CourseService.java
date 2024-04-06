package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.Course;
import edu.duke.ece651.shared.dao.CourseDAO;

public class CourseService {

    private CourseDAO courseDAO = new CourseDAO();

    public void addCourse(Course course) {
        courseDAO.addCourse(course);
    }

    public void deleteCourse(String courseId) {
        courseDAO.deleteCourse(courseId);
    }

    public void updateCourseName(String courseId, String newCourseName) {
        courseDAO.updateCourseName(courseId, newCourseName);
    }
}
