package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.Course;
import edu.duke.ece651.shared.dao.CourseDAO;
import java.util.List;

/**
 * Service class that manages course operations.
 * This class provides a high-level interface for interacting with course-related data through the CourseDAO.
 * It handles operations such as retrieving, adding, deleting, and updating courses.
 */
public class CourseService {

    private CourseDAO courseDAO = new CourseDAO();

    /**
     * Retrieves a list of all courses from the database.
     * @return a list of all courses currently available in the database.
     */
    public List<Course> findAllCourses(){
        return courseDAO.findAllCourses();
    }

    /**
     * Retrieves a specific course by its ID.
     * @param courseId the identifier of the course to retrieve.
     * @return the Course object if found, or null if no course is found with the provided ID.
     */
    public Course findCourseById(String courseId){
        return courseDAO.findCourseById(courseId);
    }

     /**
     * Adds a new course to the database.
     * @param course the Course object containing the details of the course to be added.
     */
    public void addCourse(Course course) {
        courseDAO.addCourse(course);
    }


    /**
     * Deletes a course from the database.
     * @param courseId the identifier of the course to be deleted.
     */
    public void deleteCourse(String courseId) {
        courseDAO.deleteCourse(courseId);
    }

    /**
     * Updates the name of an existing course.
     * @param courseId the identifier of the course whose name is to be updated.
     * @param newCourseName the new name to be assigned to the course.
     */
    public void updateCourseName(String courseId, String newCourseName) {
        courseDAO.updateCourseName(courseId, newCourseName);
    }
}
