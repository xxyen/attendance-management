package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class UserOperatorTest {
    //@Disabled
    @Test
    public void test_signIn() {
        UserOperator uo = new UserOperator();
        User user = uo.signIn("stu001", "123");
        assertEquals("cp357@duke.edu", user.getEmail().getEmailAddr());
    }



}