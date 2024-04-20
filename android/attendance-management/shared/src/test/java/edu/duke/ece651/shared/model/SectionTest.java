package edu.duke.ece651.shared.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SectionTest {

    @Test
    public void test_constructor() {
        Section section = new Section(1, "C101", "F10");
        assertEquals(1, section.getSectionId());
        assertEquals("C101", section.getCourseId());
        assertEquals("F10", section.getFacultyId());

        Section section2 = new Section("C102", "F10");

        assertEquals("C102", section2.getCourseId());
        assertEquals("F10", section2.getFacultyId());
    }

    @Test
    public void test_setters() {
        Section section = new Section();
        section.setSectionId(2);
        section.setCourseId("C102");
        section.setFacultyId("F20");

        assertEquals(2, section.getSectionId());
        assertEquals("C102", section.getCourseId());
        assertEquals("F20", section.getFacultyId());
    }

    @Test
    public void test_toString() {
        Section section = new Section(3, "C103", "F30");
        String expected = "Section{sectionId=3, courseId='C103', facultyId='F30'}";
        assertEquals(expected, section.toString());
    }
}
