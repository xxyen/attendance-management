package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    public void test_getter_and_setter() throws IOException {
        Email e1 = new Email("abc@gmail.com");
        Email e2 = new Email("def@duke.edu");

        Student s1 = new Student("cp357", "Can Pei", "Alex Pei", e1);

        assertEquals(s1.getDisplayName(), "Alex Pei");
        assertEquals(s1.getPersonalID(), "cp357");
        assertEquals(s1.getEmailAddr(), e1);
        assertEquals(s1.getLegalName(), "Can Pei");

        s1.setDisplayName("Peter Pei");
        s1.setEmailAddr(e2);

        assertEquals(s1.getDisplayName(), "Peter Pei");
        assertEquals(s1.getEmailAddr(), e2);

    }

    @Test
    void test_equalAndHash(){
        Email e1 = new Email("abc@gmail.com");
        Email e2 = new Email("def@duke.edu");

        Student s1 = new Student("cp357", "Can Pei", "Alex Pei", e1);
        Student s2 = new Student("cp357", "Can Pei", "Alex Pei", e1);
        Student s3 = new Student("cp357", "Can Pei", "Alex Pei", e2);
        Student s4 = new Student("cp358", "Can Pei", "Alex Pei", e1);
        Student s5 = new Student("cp357", "Can Pei1", "Alex Pei", e1);
        Student s6 = new Student("cp357", "Can Pei", "Alex Pei1", e1);
        String s7 = "Can Pei";
        Student s8 = null;

        assertTrue(s1.equals(s1));
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(s3));
        assertFalse(s1.equals(s4));
        assertFalse(s1.equals(s5));
        assertFalse(s1.equals(s6));
        assertFalse(s1.equals(s7));
        assertFalse(s1.equals(s8));

        assertEquals(s1.hashCode(), s2.hashCode());
        assertEquals(s1.getUserType(), "professor");
    }

}
