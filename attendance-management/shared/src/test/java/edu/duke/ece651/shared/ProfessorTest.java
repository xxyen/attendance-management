package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProfessorTest {
  @Test
  public void test_handleCourses() {
    Email email = "aaaa@duke.edu";
    Professor prof = new Professor("A", "aa123", email);
    prof.addCourse("ECE651");
  }

}
