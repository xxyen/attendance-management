package edu.duke.ece651.shared.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CourseTest {

    @Test
    public void test_defaultConstructor() {
        Course course = new Course();
        assertEquals(null, course.getCourseId()); 
        assertEquals(null, course.getCourseName()); 
    }

    @Test
    public void test_moreConstructor() {
        Course course = new Course("CS101", "Introduction to Computer Science");
        assertEquals("CS101", course.getCourseId());
        assertEquals("Introduction to Computer Science", course.getCourseName());
    }

    @Test
    public void test_settersAndGetters() {
        Course course = new Course();
        course.setCourseId("CS102");
        assertEquals("CS102", course.getCourseId());

        course.setCourseName("Data Structures");
        assertEquals("Data Structures", course.getCourseName()); 
    }

    @Test
    public void test_toString() {
        Course course = new Course("CS103", "Algorithms");
        String expectedString = "Course{courseId='CS103', courseName='Algorithms'}";
        assertEquals(expectedString, course.toString()); 
    }
}
