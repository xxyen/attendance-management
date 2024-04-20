package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.Course;
import edu.duke.ece651.shared.dao.CourseDAO;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.List;


public class CourseServiceTest {
    private CourseService courseService;

    @Test
    public void test_courseService() {
        courseService = new CourseService();
        Course newCourse = new Course("TEST102", "Advanced Testing");
        courseService.addCourse(newCourse);

        Course foundCourse = courseService.findCourseById("TEST102");
        List<Course> foundAllCourse = courseService.findAllCourses();
        assertNotNull(foundCourse);
        assertNotNull(foundAllCourse);
        assertEquals("Advanced Testing", foundCourse.getCourseName());

        courseService.updateCourseName("TEST102", "Intermediate Testing");

        Course updatedCourse = courseService.findCourseById("TEST102");
        assertEquals("Intermediate Testing", updatedCourse.getCourseName());

        courseService.deleteCourse("TEST102");

        courseService.deleteCourse("TEST102");

        Course deletedCourse = courseService.findCourseById("TEST102");
        assertNull(deletedCourse); 
    }

}
