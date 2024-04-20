package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.model.Course;
import java.util.List;

/**
 * The CourseDAO class provides data access operations specific to courses.
 * It extends the BasicDAO class and inherits basic data access methods.
 */
public class CourseDAO extends BasicDAO<Course> {

    /**
     * Retrieves information for all courses in the database.
     *
     * @return a list of Course objects representing all courses
     */
    public List<Course> findAllCourses() {
        String sql = "SELECT course_id AS courseId, course_name AS courseName FROM course";
        return queryMulti(sql, Course.class);
    }

    /**
     * Retrieves information for a course based on its ID.
     *
     * @param courseId the ID of the course to query
     * @return a Course object representing the queried course, or null if not found
     */
    public Course findCourseById(String courseId) {
        String sql = "SELECT course_id AS courseId, course_name AS courseName FROM course WHERE course_id = ?";
        return querySingle(sql, Course.class, courseId);
    }

    /**
     * Adds a new course to the database.
     *
     * @param course the Course object representing the course to be added
     * @return the number of rows affected by the insert operation
     */
    public int addCourse(Course course) {
        String sql = "INSERT INTO course (course_id, course_name) VALUES (?, ?)";
        return update(sql, course.getCourseId(), course.getCourseName());
    }

    /**
     * Deletes a course from the database based on its ID.
     *
     * @param courseId the ID of the course to be deleted
     * @return the number of rows affected by the delete operation
     */
    public int deleteCourse(String courseId) {
        String sql = "DELETE FROM course WHERE course_id = ?";
        return update(sql, courseId);
    }

    /**
     * Updates the name of a course in the database.
     *
     * @param courseId      the ID of the course to be updated
     * @param newCourseName the new name for the course
     * @return the number of rows affected by the update operation
     */
    public int updateCourseName(String courseId, String newCourseName) {
        String sql = "UPDATE course SET course_name = ? WHERE course_id = ?";
        return update(sql, newCourseName, courseId);
    }

    /**
     * Finds the course associated with a given section ID.
     *
     * @param sectionId the ID of the section
     * @return a Course object representing the course associated with the section, or null if not found
     */
    public Course findCourseBySectionId(int sectionId) {
        String sql = "SELECT c.course_id AS courseId, c.course_name AS courseName " +
                     "FROM course c JOIN section s ON c.course_id = s.course_id " +
                     "WHERE s.section_id = ?";
        return querySingle(sql, Course.class, sectionId);
    }
}
