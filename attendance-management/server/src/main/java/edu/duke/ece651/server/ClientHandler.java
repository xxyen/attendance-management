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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ObjectMapper mapper = new ObjectMapper();

    public ClientHandler(Socket socket) throws IOException {
        this.clientSocket = socket;
        this.output = new ObjectOutputStream(clientSocket.getOutputStream());
        this.output.flush();
        this.input = new ObjectInputStream(clientSocket.getInputStream());
    }

    private void mainLoop() throws IOException, ClassNotFoundException {
        boolean flag = true;
        while (flag) {
            try {
                if (clientSocket.isClosed()) {
                    System.out.println("Client has closed the connection.");
                    flag = false; 
                    continue;
                }
    
                String jsonCredentials = (String) input.readObject();
                Map<String, String> credentials = mapper.readValue(jsonCredentials, Map.class);
                
                User user = userOperator.signIn(credentials.get("userid"), credentials.get("password"));
                
                if (user instanceof Professor) {
                    Professor p = (Professor) user;
                    output.writeObject(p.getName()); 
                    output.flush();

                    profLoop(p);

                } else {
                    output.writeObject("User is not a professor.");
                    output.flush();
                }

            } catch (EOFException e) {
                System.out.println("Client disconnected.");
                flag = false; 
            }catch (IllegalArgumentException e) {
                output.writeObject(e.getMessage());
                output.flush();
            }
            output.flush();
        }
    }

    public void profLoop(Professor p) throws IOException {
            boolean flag = true;
    
            while (flag) {
                try {
                    String json = (String) input.readObject();
                    Map<String, String> command = mapper.readValue(json, Map.class);
                    
                    if ("Logout".equals(command.get("action"))) {
                        System.out.println("Client requested logout.");
                        flag = false;
                        break;
                    } else if ("ManipulateSections".equals(command.get("action"))) {
                        System.out.println("Client requested to manipulate sections.");
                        Section s = chooseSection(p);
                        ProfTextPlayer player = new ProfTextPlayer(p, s, input, output);
                        player.loop();
                    }
    
                } catch (EOFException e) {
                    System.out.println("Client disconnected.");
                    flag = false; 
                }catch (Exception e) {
                    output.writeObject(e.getMessage());
                    output.flush();
                }
                output.flush();
            }
        }

    public Section chooseSection(Professor p) throws IOException,ClassNotFoundException {
        List<Section> sectionList = new ArrayList<>();
        sectionList = sectionDAO.querySectionByFaculty(p.getUserid());

        HashMap<String, List<String>> sectionsData = new HashMap<>();
        List<String> sectionsInfo = new ArrayList<>();

        for (Section section : sectionList) {
            sectionsInfo.add("Course: " + section.getCourseId() + ", Sec: " + section.getSectionId());
        }
        
        sectionsData.put("sections", sectionsInfo);
        String jsonSections = mapper.writeValueAsString(sectionsData);
        output.writeObject(jsonSections);
        output.flush();

        int index = 1;
        HashMap<String, Object> command = new ObjectMapper().readValue((String)input.readObject(), HashMap.class);
        if ("SelectSection".equals(command.get("action"))) {
            index = (Integer) command.get("index");
            System.out.println("Server received selected section index: " + index);
        }

        System.out.println("Server go to selected section id: " + sectionList.get(index).getSectionId());
        return sectionList.get(index);
    }


    // public void stuLoop(Student s, BufferedReader input, PrintStream output){
    //     boolean flag = true;

    //     while (flag) {
    //         try {
    //             output.print("--------------------------------------------------------------------------------\n");
    //             output.print("Below are all the available actions:\n" +
    //                     "1. Manipulate on your sections.\n" +
    //                     "2. Log out.\n" +
    //                     "What do you want to do? Please type in the index number:\n");
    //             output.print("--------------------------------------------------------------------------------\n");
    //             output.println();

    //             int index = ReaderUtilities.readPositiveInteger(input);

    //             if (index == 1){
    //                 Section ses = chooseSection(s, input, output);
    //                 //todo:
    //                 //run student loop
    //                 StuTextPlayer player = new StuTextPlayer(input, output, ses, s);
    //                 player.loop();

    //             }
    //             else if (index == 2){
    //                 output.println("Successfully log out!");
    //                 flag = false;
    //                 break;
    //             }
    //             else {
    //                 throw new IllegalArgumentException("Invalid action number, please choose your action again!");
    //             }

    //         } catch (Exception e) {
    //             output.println(e.getMessage());
    //         }
    //     }
    // }

    @Override
    public void run() {
        try {
            mainLoop();
            clientSocket.close();
        } catch (Exception e) {
            System.err.println("Exception in client handler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
