package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class TextPlayerTest {
    private TextPlayer createTextPlayer(String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Professor p = new Professor("test Prof", "abc123", new Email("test@gmail.com"));
        //ArrayList<Professor> professorsArray = new ArrayList<Professor>();
        Professor[] professorsArray = {p};
//        ArrayList<Student> studentArray = new ArrayList<>();
//        studentArray.add(new Student("1", "test stu1", "test disstu1", new Email("stu1.gmail.com")));
//        studentArray.add(new Student("2", "test stu2", "test disstu2", new Email("stu2.gmail.com")));

        Student[] studentArray = {new Student("1", "test stu1", "test disstu1", new Email("stu1@gmail.com")),
                new Student("2", "test stu2", "test disstu2", new Email("stu2@gmail.com"))};
        Course c = new Course("c1", professorsArray, studentArray, true);
        AttendanceOperator ope = new BasicAttendanceOperator();
        FileHandler f = new FileHandler();

        TextPlayer t = new TextPlayer(p, c, ope, f, input, output);
        return t;
    }

    @Test
    void test_readStatus() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("a\nx\n8\nC8H\n", bytes);
        String prompt = "test readStatus:\n";

        StringBuilder s = new StringBuilder("--------------------------------------------------------------------------------\n");
        s.append(prompt);
        s.append("--------------------------------------------------------------------------------\n");

        Status s1 = player.readStatus(prompt);
        //Status e1 = new Status('a');
        assertEquals(s1.getStatus(), 'a'); //did we get the right status back
        assertEquals(s.toString() + "\n", bytes.toString()); //should have printed prompt and newline

        assertThrows(IllegalArgumentException.class, () -> player.readStatus(prompt));
        assertThrows(IllegalArgumentException.class, () -> player.readStatus(prompt));
        assertThrows(IllegalArgumentException.class, () -> player.readStatus(prompt));
        assertThrows(IllegalArgumentException.class, () -> player.readStatus(prompt));

    }

    @Test
    void test_takeAttendance() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("a\nx\n8\nC8H\np\n", bytes);
        String prompt = "Please type in the attendance status of this student. ('p' for present, 'a' for absent)\n";

        StringBuilder s = new StringBuilder("--------------------------------------------------------------------------------\n");
        s.append("test disstu1\n");
        s.append("1\n");
        s.append("--------------------------------------------------------------------------------\n");
        s.append("\n");

        s.append("--------------------------------------------------------------------------------\n");
        s.append(prompt);
        s.append("--------------------------------------------------------------------------------\n");
        s.append("\n");

        s.append("--------------------------------------------------------------------------------\n");
        s.append("test disstu2\n");
        s.append("2\n");
        s.append("--------------------------------------------------------------------------------\n");
        s.append("\n");

        s.append("--------------------------------------------------------------------------------\n");
        s.append(prompt);
        s.append("--------------------------------------------------------------------------------\n");
        s.append("\n");

        s.append("Invalid status: x\n");
        s.append("--------------------------------------------------------------------------------\n");
        s.append(prompt);
        s.append("--------------------------------------------------------------------------------\n");
        s.append("\n");

        s.append("Invalid input: you should only type in one letter!\n");
        s.append("--------------------------------------------------------------------------------\n");
        s.append(prompt);
        s.append("--------------------------------------------------------------------------------\n");
        s.append("\n");

        s.append("Invalid input: you should only type in one letter!\n");
        s.append("--------------------------------------------------------------------------------\n");
        s.append(prompt);
        s.append("--------------------------------------------------------------------------------\n");
        s.append("\n");

        Session ses = player.takeAttendance();

        assertEquals(s.toString(), bytes.toString()); //should have printed prompt and newline

        List<AttendanceRecord> records = ses.getRecords();
        assertEquals(records.get(0).getStatus().getStatus() , 'a');
        assertEquals(records.get(0).getStudent().getDisplayName(), "test disstu1");
        assertEquals(records.get(1).getStatus().getStatus() , 'p');
        assertEquals(records.get(1).getStudent().getDisplayName(), "test disstu2");
        System.out.println(ses.getTime());
    }
}