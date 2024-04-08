package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.model.Course;
import java.util.List;

public class CourseDAO extends BasicDAO<Course> {

    public List<Course> findAllCourses() {
        String sql = "SELECT course_id AS courseId, course_name AS courseName FROM course";
        return queryMulti(sql, Course.class);
    }

    public Course findCourseById(String courseId) {
        String sql = "SELECT course_id AS courseId, course_name AS courseName FROM course WHERE course_id = ?";
        return querySingle(sql, Course.class, courseId);
    }

    public int addCourse(Course course) {
        String sql = "INSERT INTO course (course_id, course_name) VALUES (?, ?)";
        return update(sql, course.getCourseId(), course.getCourseName());
    }

    public int deleteCourse(String courseId) {
        String sql = "DELETE FROM course WHERE course_id = ?";
        return update(sql, courseId);
    }

    public int updateCourseName(String courseId, String newCourseName) {
        String sql = "UPDATE course SET course_name = ? WHERE course_id = ?";
        return update(sql, newCourseName, courseId);
    }
}
