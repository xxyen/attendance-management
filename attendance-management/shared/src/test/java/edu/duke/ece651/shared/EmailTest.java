package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EmailTest {
    @Test
    public void test_constructor(){
        Email e1 = new Email("abc@duke.edu");
        assertEquals(e1.getEmailAddr(), "abc@duke.edu");

        assertThrows(IllegalArgumentException.class, ()->new Email(""));
        assertThrows(IllegalArgumentException.class, ()->new Email("ddc gmail.com"));
        assertThrows(IllegalArgumentException.class, ()->new Email("ewc@gmail..com"));


    }

}