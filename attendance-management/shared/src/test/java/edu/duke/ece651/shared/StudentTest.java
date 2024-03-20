package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    public void test_getter_and_setter(){
        Email e1 = new Email("abc@gmail.com");
        Email e2 = new Email("def@duke.edu");

        Student s1 = new Student("cp357", "Can Pei", "Alex Pei", e1);

        assertEquals(s1.getDisplayName(), "Alex Pei");
        assertEquals(s1.getStudentID(), "cp357");
        assertEquals(s1.getEmailAddr(), e1);
        assertEquals(s1.getLegalName(),"Can Pei");

        s1.setDisplayName("Peter Pei");
        s1.setEmailAddr(e2);

        assertEquals(s1.getDisplayName(), "Peter Pei");
        assertEquals(s1.getEmailAddr(), e2);

    }

}