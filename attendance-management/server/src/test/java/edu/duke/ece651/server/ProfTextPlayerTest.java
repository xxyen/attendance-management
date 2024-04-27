// package edu.duke.ece651.server;

// import static org.junit.jupiter.api.Assertions.*;

// import edu.duke.ece651.shared.*;
// import edu.duke.ece651.shared.model.Section;
// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.Test;

// import java.io.*;
// import java.time.Duration;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;


// class ProfTextPlayerTest {
//     private ProfTextPlayer createTextPlayer(String inputData, OutputStream bytes) {
//         BufferedReader input = new BufferedReader(new StringReader(inputData));
//         PrintStream output = new PrintStream(bytes, true);
//         Professor p = new Professor("prof001", "adh39", new Email("test@gmail.com"));
//         // ArrayList<Professor> professorsArray = new ArrayList<Professor>();
//         //Professor[] professorsArray = { p };
//         // ArrayList<Student> studentArray = new ArrayList<>();
//         // studentArray.add(new Student("1", "test stu1", "test disstu1", new
//         // Email("stu1.gmail.com")));
//         // studentArray.add(new Student("2", "test stu2", "test disstu2", new
//         // Email("stu2.gmail.com")));

//         Section s = new Section(5, "ece651", p.getUserid());

//         ProfTextPlayer t = new ProfTextPlayer(p, s, input, output);
//         return t;
//     }

//     @Test
//     void test_readFormat() throws IOException {
//         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//         ProfTextPlayer player = createTextPlayer("xml\njson\nabv\n", bytes);
//         assertEquals("xml", player.readFormat("a"));
//         assertEquals("json", player.readFormat("a"));
//         assertThrows(IllegalArgumentException.class, () -> player.readFormat("a"));
//         assertThrows(IOException.class, () -> player.readFormat("a"));

//     }
//     @Test
//     void test_readSingleInt() throws IOException {
//         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//         ProfTextPlayer player = createTextPlayer("-1\n", bytes);
//         BufferedReader input = player.getInputReader();
//         assertThrows(IllegalArgumentException.class, () -> player.readPositiveInteger(input));

//     }

//     //@Disabled
//     @Test
//     void test_exportSession() throws Exception {
//         String workingDir = System.getProperty("user.dir");
//         String path = workingDir + "/export/";

//         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//         ProfTextPlayer player = createTextPlayer(
//                 "json\n" +
//                         "xml\n/", bytes);
//         //player.full_takeAttendance();
//         player.exportSessions();
//         player.exportSessions();
//     }

//     @Test
//     void test_loop() throws Exception {
//         assertTimeout(Duration.ofMinutes(1), () -> {
//             ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//             ProfTextPlayer player = createTextPlayer("abc\n" +
//                     "9\n" +
//                     "1\n" +
//                     "wef\n" +
//                     "1\n" +
//                     "8\n" +
//                     "1\n" +
//                     "2\n" +
//                     "fwegasg\n" +
//                     "1\n" +
//                     "2\n" +
//                     "12/31/2042\n" +
//                     "1\n" +
//                     "2\n" +
//                     "12/31/2020\n" +
//                     "awaf\n" +
//                     "y\n" +
//                     "a\n" +
//                     "1\n" +
//                     "1\n" +
//                     "afwe\n" +
//                     "e\n" +
//                     "a\n" +
//                     "2\n" +
//                     "100\n" +
//                     "2\n" +
//                     "1\n" +
//                     "adsf\n" +
// //                    "2\n" +
// //                    "1\n" +
// //                    "stu001\n" +
// //                    "t\n" +
//                     "3" +
//                     "asdfas\n" +
//                     "3\n" +
//                     "xml\n" +
//                     "3\n" +
//                     "json\n" +
//                     "4\n", bytes);
//             player.loop();
//         });

//     }


// }