package edu.duke.ece651.shared.dao;
import edu.duke.ece651.shared.dao.CourseDAO;
import edu.duke.ece651.shared.model.Course;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


import org.junit.jupiter.api.Test;

public class CourseDAOTest {
  private CourseDAO courseDAO;
  @Test
    public void test_addAndFindCourse() {
      courseDAO = new CourseDAO();
        Course newCourse = new Course("TEST101", "Introduction to Testing");
        int result = courseDAO.addCourse(newCourse);
        assertEquals(1, result);

        Course foundCourse = courseDAO.findCourseById("TEST101");
        System.out.println(foundCourse);

        assertNotNull(foundCourse);
        assertEquals("Introduction to Testing", foundCourse.getCourseName());

        List<Course> allCourses = courseDAO.findAllCourses();
        System.out.println("All courses:");
        for (Course course : allCourses) {
          System.out.println(course);
        }
        courseDAO.updateCourseName("TEST101", "Testing");
        assertEquals("Testing", courseDAO.findCourseById("TEST101").getCourseName());
        courseDAO.deleteCourse("TEST101");
    }
}
