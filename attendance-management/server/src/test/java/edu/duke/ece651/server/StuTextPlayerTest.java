package edu.duke.ece651.server;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.model.Section;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class StuTextPlayerTest {
    private StuTextPlayer createTextPlayer(String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);

        Student p = new Student("stu001", "adh39", "john", new Email("test@gmail.com"));
        // ArrayList<Professor> professorsArray = new ArrayList<Professor>();
        //Professor[] professorsArray = { p };
        // ArrayList<Student> studentArray = new ArrayList<>();
        // studentArray.add(new Student("1", "test stu1", "test disstu1", new
        // Email("stu1.gmail.com")));
        // studentArray.add(new Student("2", "test stu2", "test disstu2", new
        // Email("stu2.gmail.com")));

        Section s = new Section(5, "ece651", p.getUserid());

        StuTextPlayer t = new StuTextPlayer(input, output, s, p);
        return t;
    }

    @Test
    void test_loop() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        StuTextPlayer player = createTextPlayer("abc\n" +
                "9\n" +
                "1\n" +
                "asdf\n" +
                "1\n" +
                "t\n" +
                "1\n" +
                "y\n" +
                "1\n" +
                "Y\n" +
                "1\n" +
                "n\n" +
                "1\n" +
                "N\n" +
                "2\n" +
                "afwed\n" +
                "2\n" +
                "k\n" +
                "2\n" +
                "s\n" +
                "2\n" +
                "S\n" +
                "2\n" +
                "d\n" +
                "asdfsd\n" +
                "2\n" +
                "d\n" +
                "xml\n" +
                "2\n" +
                "D\n" +
                "json\n" +
                "3\n", bytes);
        player.loop();

    }

}