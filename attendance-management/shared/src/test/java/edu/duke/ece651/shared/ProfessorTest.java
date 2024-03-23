package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProfessorTest {
  @Test
  public void test_handleCourses() {
    Email email = new Email("aaaa@bbb.com");
    Professor prof = new Professor("A", "aa123", email);
    prof.addCourse("ECE651");
    assertTrue(prof.hasCourse("ECE651"));
    assertFalse(prof.hasCourse("ECE568"));
    prof.addCourse("ECE568");
    assertTrue(prof.hasCourse("ECE651"));
    assertTrue(prof.hasCourse("ECE568"));
    prof.removeCourse("ECE651");
    assertFalse(prof.hasCourse("ECE651"));
    assertTrue(prof.hasCourse("ECE568"));
    assertThrows(IllegalArgumentException.class, ()->prof.addCourse("ECE568"));
    assertThrows(IllegalArgumentException.class, ()->prof.removeCourse("ECE651"));
  }

}
