package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EmailTest {
    @Test
    public void test_constructor(){
        Email e1 = new Email("abc@duke.edu");
        assertEquals(e1.getEmailAddr(), "abc@duke.edu");

        String test = null;
        assertThrows(IllegalArgumentException.class, ()->new Email(test));
        assertThrows(IllegalArgumentException.class, ()->new Email("ddc gmail.com"));
        assertThrows(IllegalArgumentException.class, ()->new Email("ewc@gmail..com"));


    }

    @Test
    void test_equalsAndHash(){
        Email e1 = new Email("abc@duke.edu");
        Email e2 = new Email("abc@duke.edu");
        Email e3 = new Email("def@duke.edu");
        Email e4 = null;
        String e5 = "abc@duke.edu";
        assertTrue(e1.equals(e1));
        assertTrue(e1.equals(e2));
        assertFalse(e1.equals(e3));
        assertFalse(e1.equals(e4));
        assertFalse(e1.equals(e5));



    }

}