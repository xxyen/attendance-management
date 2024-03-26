package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class TextPlayerTest {
    private TextPlayer createTextPlayer(String inputData, OutputStream bytes, boolean canChangeName) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Professor p = new Professor("test Prof", "abc123", new Email("test@gmail.com"));
        // ArrayList<Professor> professorsArray = new ArrayList<Professor>();
        Professor[] professorsArray = { p };
        // ArrayList<Student> studentArray = new ArrayList<>();
        // studentArray.add(new Student("1", "test stu1", "test disstu1", new
        // Email("stu1.gmail.com")));
        // studentArray.add(new Student("2", "test stu2", "test disstu2", new
        // Email("stu2.gmail.com")));

        Student[] studentArray = { new Student("1", "test stu1", "test disstu1", new Email("cp357@duke.edu")),
                new Student("2", "test stu2", "test disstu2", new Email("stu2@gmail.com")) };
        Course c = new Course("c1", professorsArray, studentArray, canChangeName);
        // c.addSession(new Session(c.getCourseid(), new Date()));
        // c.addSession(new Session(c.getCourseid(), new Date()));
        AttendanceOperator ope = new BasicAttendanceOperator();
        FileHandler f = new FileHandler();

        TextPlayer t = new TextPlayer(p, c, ope, input, output);
        return t;
    }

    @Test
    void test_changeDisplayName() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("1\nfk\n1\ntest disstu1\n2\n", bytes, true);
        player.changeDisplayName();
        List<Student> studentList = player.getStudent();
        assertEquals("fk", studentList.get(0).getDisplayName());

        player.changeDisplayName();
        studentList = player.getStudent();
        assertEquals("test disstu1", studentList.get(0).getDisplayName());

        assertThrows(IOException.class, () -> player.changeDisplayName());

        TextPlayer player2 = createTextPlayer("1\nfk\n1\ntest disstu1\n2\n", bytes, false);
        assertThrows(IllegalArgumentException.class, () -> player2.changeDisplayName());

        // System.out.println(bytes.toString());

    }

    @Test
    // @Disabled
    void test_readStatus() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("a\nx\n8\nC8H\n", bytes, true);
        String prompt = "test readStatus:\n";

        StringBuilder s = new StringBuilder(
                "--------------------------------------------------------------------------------\n");
        s.append(prompt);
        s.append("--------------------------------------------------------------------------------\n");

        Status s1 = player.readStatus(prompt);
        // Status e1 = new Status('a');
        assertEquals(s1.getStatus(), 'a'); // did we get the right status back
        assertEquals(s.toString() + "\n", bytes.toString()); // should have printed prompt and newline

        assertThrows(IllegalArgumentException.class, () -> player.readStatus(prompt));
        assertThrows(IllegalArgumentException.class, () -> player.readStatus(prompt));
        assertThrows(IllegalArgumentException.class, () -> player.readStatus(prompt));
        assertThrows(IllegalArgumentException.class, () -> player.readStatus(prompt));

    }

    @Test
    // @Disabled
    void test_takeAttendance() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("a\nx\n8\nC8H\np\n", bytes, true);
        String prompt = "Please type in the attendance status of this student. ('p' for present, 'a' for absent, 'l' for late)\n";

        StringBuilder s = new StringBuilder(
                "--------------------------------------------------------------------------------\n");
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

        assertEquals(s.toString(), bytes.toString()); // should have printed prompt and newline

        List<AttendanceRecord> records = ses.getRecords();
        assertEquals(records.get(0).getStatus().getStatus(), 'a');
        assertEquals(records.get(0).getStudent().getDisplayName(), "test disstu1");
        assertEquals(records.get(1).getStatus().getStatus(), 'p');
        assertEquals(records.get(1).getStudent().getDisplayName(), "test disstu2");
        // System.out.println(ses.getTime());
    }

    @Test
    // @Disabled
    void test_searchStudent() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("a\nx\n8\nC8H\np\n", bytes, true);
        Student test1 = new Student("1", "test stu1", "test disstu1", new Email("cp357@duke.edu"));
        assertEquals(test1, player.searchStudent("1"));
        assertEquals(null, player.searchStudent("abc"));
    }

    @Test
    // @Disabled
    void test_dosearchStudent() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("2\ncx\n8\n", bytes, true);
        Student test1 = new Student("2", "test stu2", "test disstu2", new Email("stu2@gmail.com"));
        assertEquals(test1, player.doSearchStudent());

        String prompt = "Please type in the student's id:\n";

        StringBuilder s = new StringBuilder(
                "--------------------------------------------------------------------------------\n");
        s.append(prompt);
        s.append("--------------------------------------------------------------------------------\n");
        s.append("\n");
        assertEquals(s.toString(), bytes.toString());
        assertThrows(IllegalArgumentException.class, () -> player.doSearchStudent());
        assertThrows(IllegalArgumentException.class, () -> player.doSearchStudent());
        assertThrows(IOException.class, () -> player.doSearchStudent());

    }

    @Test
    @Disabled
    void test_chooseSession() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("a\np\n1\n8\n-1\nxyz\n", bytes, true);
        Session ses = player.takeAttendance();
        player.addSession(ses);
        assertEquals(ses, player.chooseSession());
        // System.out.println(bytes.toString());
        assertThrows(IllegalArgumentException.class, () -> player.chooseSession());
        assertThrows(IllegalArgumentException.class, () -> player.chooseSession());

        assertThrows(IllegalArgumentException.class, () -> player.chooseSession());

    }

    @Test
    void test_addAndRemoveStudent() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("test1\ntest1\n", bytes, true);
        player.addStudent();
        Student exp = new Student("test1", "Can Pei", "Alex", new Email("cp357@duke.edu"));
        List<Student> studentList = player.getStudent();
        assertEquals(3, studentList.size());
        assertEquals(exp, studentList.get(2));

        player.removeStudent();
        studentList = player.getStudent();
        assertEquals(2, studentList.size());

        // System.out.println(bytes.toString());
        assertThrows(IOException.class, () -> player.addStudent());
    }

    @Test
    //@Disabled
    void test_changeStatus() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("a\np\n" +
                "1\n1\nl\n" +
                "test1\n1\ntest1\na\ntest1\n" +
                "2\n" +
                "-1\n" +
                "x\n" +
                "0\n", bytes, true);
        Session ses = player.takeAttendance();
        player.addSession(ses);

        player.changeStatus();

        player.addStudent();
        player.changeStatus();
        player.removeStudent();

        assertThrows(IllegalArgumentException.class, () -> player.chooseSession());
        assertThrows(IllegalArgumentException.class, () -> player.chooseSession());
        assertThrows(IllegalArgumentException.class, () -> player.chooseSession());
        assertThrows(IllegalArgumentException.class, () -> player.chooseSession());

        System.out.println(bytes.toString());

    }

    @Test
    void test_readFormat() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("xml\njson\nabv\n", bytes, true);
        assertEquals("xml", player.readFormat("a"));
        assertEquals("json", player.readFormat("a"));
        assertThrows(IllegalArgumentException.class, () -> player.readFormat("a"));
    }

    @Disabled
    @Test
    void test_exportSession() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("a\np\n" +
                "json\n/home/wille/Desktop/test1.json\n" +
                "xml\n/home/wille/Desktop/test2.xml\n", bytes, true);
        player.full_takeAttendance();
        player.exportSessions();
        player.exportSessions();
    }

}