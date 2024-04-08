package edu.duke.ece651.server;

import edu.duke.ece651.shared.AttendanceOperator;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.model.Course;
import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.model.Section;
import edu.duke.ece651.shared.model.Session;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable{
    private Socket clientSocket;

    private UserOperator userOperator = new UserOperator();

    private static FacultyDAO facultyDAO = new FacultyDAO();
    private static StudentDAO studentDAO = new StudentDAO();
    private static GlobalSettingDAO gsDAO = new GlobalSettingDAO();
    private static AttendanceRecordDAO attendanceRecordDAO= new AttendanceRecordDAO();
    private static CourseDAO courseDAO = new CourseDAO();
    private static SectionDAO sectionDAO = new SectionDAO();
    private static EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private static SessionDAO sessionDAO = new SessionDAO();

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void mainLoop(BufferedReader input, PrintStream output){
        boolean flag = true;

        while (flag) {
            try {
                output.print("--------------------------------------------------------------------------------\n");
                output.print("Hello! Below are all the available actions:\n" +
                        "1. Log in.\n" +
                        "2. Exit this program.\n" +
                        "What do you want to do? Please type in the index number:\n");
                output.println("--------------------------------------------------------------------------------");
                output.println();

                int index = ReaderUtilities.readPositiveInteger(input);

                if (index == 1){
                    User user = signIn(input, output);
                    //todo:
                    //do Prof or Stu action

                    if (user.getUserType() == "professor"){
                        Professor p = (Professor) user;
                        output.println("Login successful. Welcome, Professor" + p.getName() + "!");
                        output.println();
                        profLoop(p, input, output);
                    }

                    else if (user.getUserType() == "student"){
                        Student s = (Student) user;
                        output.println("Login successful. Welcome, Student" + s.getDisplayName() + "!");
                        output.println();
                        stuLoop(input, output);
                    }

                }
                else if (index == 2){
                    output.println("Have a nice day! From ECE 651 team 6.");
                    output.println("endConnection");
                    output.println();
                    flag = false;
                    break;
                }
                else {
                    throw new IllegalArgumentException("Invalid action number, please choose your action again!");
                }

            } catch (Exception e) {
                output.println(e.getMessage());
                output.println();
            }
        }
    }

    public User signIn(BufferedReader input, PrintStream output) throws Exception {
        output.println("Please enter your userid:");
        output.println();
        String userid = input.readLine();
        output.println("Please enter your password:");
        output.println();
        String password = input.readLine();

        User user = userOperator.signIn(userid, password);
        // 登录成功，发送欢迎信息
        //output.println("Login successful. Welcome, " + user.getUserid() + "!");

        return user;

    }

    public Section chooseSection(Professor p, BufferedReader in, PrintStream out) throws IOException {
        List<Section> sectionList = sectionDAO.querySectionByFaculty(p.getUserid());
        int size = sectionList.size();

        for (int i = 0; i < size; i++) {
            out.println(Integer.toString(i + 1) + ". Course:" + sectionList.get(i).getCourseId() + ", Sec: " + sectionList.get(i).getSectionId());
        }
        out.print("--------------------------------------------------------------------------------\n");
        out.println(
                "The above is a list of all your sections, please enter the serial number of the section you want to choose.");
        out.println("--------------------------------------------------------------------------------\n");
        out.println();
        int index = ReaderUtilities.readPositiveInteger(in);
        if (index > size) {
            throw new IllegalArgumentException("Invalid input: there is no such a section!");
        }
        return sectionList.get(index - 1);
        //return null;
    }

    public void profLoop(Professor p, BufferedReader input, PrintStream output){
        boolean flag = true;

        while (flag) {
            try {
                output.print("--------------------------------------------------------------------------------\n");
                output.print("Below are all the available actions:\n" +
                        "1. Manipulate on your sections.\n" +
                        "2. Log out.\n" +
                        "What do you want to do? Please type in the index number:\n");
                output.println("--------------------------------------------------------------------------------\n");
                output.println();

                int index = ReaderUtilities.readPositiveInteger(input);

                if (index == 1){
                    //todo:
                    //display section list and choose
                    Section s = chooseSection(p, input, output);
                    ProfTextPlayer player = new ProfTextPlayer(p, s, input, output);
                    player.loop();
                }
                else if (index == 2){
                    output.println("Successfully log out!");
                    output.println();

                    flag = false;
                    break;
                }
                else {
                    throw new IllegalArgumentException("Invalid action number, please choose your action again!");
                }

            } catch (Exception e) {
                output.println(e.getMessage());
                output.println();
            }
        }
    }

    public void stuLoop(BufferedReader input, PrintStream output){
        boolean flag = true;

        while (flag) {
            try {
                output.print("--------------------------------------------------------------------------------\n");
                output.print("Below are all the available actions:" +
                        "1. Manipulate on your sections.\n" +
                        "2. Log out.\n" +
                        "What do you want to do? Please type in the index number:\n");
                output.println("--------------------------------------------------------------------------------\n");

                int index = ReaderUtilities.readPositiveInteger(input);

                if (index == 1){
                    //todo:
                    //display section list and choose

                }
                else if (index == 2){
                    output.println("Successfully log out!");
                    flag = false;
                    break;
                }
                else {
                    throw new IllegalArgumentException("Invalid action number, please choose your action again!");
                }

            } catch (Exception e) {
                output.println(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        try (
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintStream output = new PrintStream(clientSocket.getOutputStream(), true)
        ) {
            mainLoop(input, output);

            // 处理完成后关闭连接
            clientSocket.close();
        } catch (Exception e) {
            System.err.println("Exception in client handler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
