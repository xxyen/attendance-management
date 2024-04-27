package edu.duke.ece651.server;

//package edu.duke.ece651.shared;

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.model.*;

import java.io.*;
import java.security.GeneralSecurityException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.HashMap;

/**
 * This class is used to display information to user
 * and take action from user.
 * @author Can Pei
 * @version 1.0
 */
public class ProfTextPlayer {
    private Professor professor;
    //private Course course;
    private Section section;

    // private final BufferedReader inputReader;
    // private final PrintStream out;


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

    public ProfTextPlayer(Professor professor, Section section, ObjectInputStream input, ObjectOutputStream output) {
        this.professor = professor;
        this.section = section;
        this.input = input;
        this.output = output;
    }

    /**
     * Constructor
     */

    // public ProfTextPlayer(Professor professor, Section section, BufferedReader inputReader,
    //                       PrintStream out) {
    //     this.professor = professor;
    //     //this.course = course;
    //     this.section = section;
    //     this.inputReader = inputReader;
    //     this.out = out;

    // }


    /**
     * Read one single letter from input,
     * used for readStatus.
     */

    // public static char readSingleLetter(BufferedReader reader) throws IOException {
    //     String line = reader.readLine();
    //     if (line != null && line.length() == 1 && Character.isLetter(line.charAt(0))) {
    //         return line.charAt(0);
    //     } else {
    //         throw new IllegalArgumentException("Invalid input: you should only type in one letter!");
    //     }
    // }

    /**
     * Read a status from input.
     */
    // public Status readStatus(String prompt) throws IOException {
    //     out.print("--------------------------------------------------------------------------------\n");
    //     out.print(prompt);
    //     out.print("--------------------------------------------------------------------------------\n");
    //     out.println();
    //     char status = readSingleLetter(inputReader);
    //     return new Status(status);
    // }

    /**
     * Take attendance record for all the student of this course.
     * @return a Session of the records
     */
    // public Session takeAttendance(Date time) throws IOException {
    //      Session newSes = new Session(section.getSectionId(), time, new Time(time.getTime()), new Time(time.getTime()));
    //      sessionDAO.addSession(newSes);
    //      for (Enrollment e: enrollmentDAO.listEnrollmentsBySection(section.getSectionId())) {
    //          Student s = studentDAO.queryStudentById(e.getStudentId());
    //          out.print("--------------------------------------------------------------------------------\n");
    //          out.println(s.getDisplayName());
    //          out.println(s.getUserid());
    //          out.print("--------------------------------------------------------------------------------\n");

    //          boolean flag = true;
    //          while (flag) {
    //              try {
    //                  Status newStatus = readStatus(
    //                          "Please type in the attendance status of this student. ('p' for present, 'a' for absent, 't' for tardy)\n");
    //                  //how to get new session's id?
    //                  AttendanceRecord tempR = new AttendanceRecord(newSes.getSessionId(), s.getUserid(), newStatus);
    //                  //newSes.addRecord(tempR);
    //                  attendanceRecordDAO.addAttendanceRecord(tempR);
    //                  flag = false;
    //              } catch (IllegalArgumentException ex) {
    //                  out.println(ex.getMessage());
    //                  // doOnePlacement(s, this.shipCreationFns.get(s));
    //              }
    //          }
    //      }
    //      return newSes;
    // }


    // private static Date convertStringToDate(String inputDate) throws Exception {
    //     SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    //     formatter.setLenient(false); // 设置解析日期的严格模式
    //     return formatter.parse(inputDate); // 尝试解析输入的字符串
    // }

    // public Date readDate() throws Exception {
    //     out.print("--------------------------------------------------------------------------------\n");
    //     out.print("Please type in the date (MM/DD/YYYY) that you want to record attendance for:\n");
    //     out.print("--------------------------------------------------------------------------------\n");
    //     out.println();

    //     String s = inputReader.readLine();

    //     Date date = convertStringToDate(s);
    //     if (!date.before(new Date())) { // 检查日期是否早于当前日期
    //         throw new IllegalArgumentException("Invalid date: the date you typed is not earlier than the current date!");
    //     }
    //     return date;
    // }

    /**
     * Full action of take attendance of a new session.
     */
    // public void full_takeAttendance() throws Exception {
    //     out.print("--------------------------------------------------------------------------------\n");
    //     out.print("Below are all the available actions:\n" +
    //             "1. Take attendance for today.\n" +
    //             "2. Take attendance for a previous date.\n" +
    //             "What do you want to do? Please type in the index number:\n");
    //     out.print("--------------------------------------------------------------------------------\n");
    //     out.println();

    //     int index = readPositiveInteger(inputReader);
    //     if (index == 1){
    //         takeAttendance(new Date());
    //     }
    //     else if (index == 2){
    //         takeAttendance(readDate());
    //     }
    //     else {
    //         throw new IllegalArgumentException("Invalid action number!");
    //     }

    // }

    /**
     * Search student with given ID.
     * @param id is the student's ID.
     */
    // public Student searchStudent(String id) {
    //     //todo
    //     //search student by id in the section
    //     //return studentDAO.queryStudentById(id);
    //     Enrollment e = enrollmentDAO.findEnrollmentByStudentAndSection(id, section.getSectionId());
    //     if (e == null){
    //         return null;
    //     }
    //     return studentDAO.queryStudentById(e.getStudentId());
    // }

    /**
     * Full action of searching a student.
     */
    // public Student doSearchStudent() throws IOException {
    //     out.print("--------------------------------------------------------------------------------\n");
    //     out.print("Please type in the student's id:\n");
    //     out.print("--------------------------------------------------------------------------------\n");
    //     out.println();

    //     String s = inputReader.readLine();
    //     if (s == null) {
    //         throw new EOFException(
    //                 "You didn't type in any instruction!\n");
    //     }
    //     Student ans = searchStudent(s);
    //     if (ans == null) {
    //         throw new IllegalArgumentException("Invalid input: there is no student with this id in this course!");
    //     }
    //     return ans;
    // }

    /**
     * Read a positive integer from input.
     * Used for choosing action or session.
     */
    // public int readPositiveInteger(BufferedReader reader) throws IOException {
    //     String line = reader.readLine(); // 从BufferedReader读取一行
    //     try {
    //         int number = Integer.parseInt(line); // 尝试将读取的行转换为整数
    //         if (number > 0) {
    //             return number; // 如果是正整数，则返回
    //         } else {
    //             throw new IllegalArgumentException("Invalid input: it is not a positive integer!"); // 如果不是正整数，抛出异常
    //         }
    //     } catch (NumberFormatException e) {
    //         // 如果转换失败（即输入不是整数），也抛出IllegalArgumentException
    //         throw new IllegalArgumentException("Invalid input: it is not a positive integer!");
    //     }
    // }

    /**
     * Choose a session from the list.
     */
    // public Session chooseSession() throws IOException {
    //      List<Session> sessionList = sessionDAO.listSessionsBySection(section.getSectionId());
    //      int size = sessionList.size();

    //      for (int i = 0; i < size; i++) {
    //          out.println(Integer.toString(i + 1) + ": Date " + sessionList.get(i).getSessionDate());
    //      }
    //      out.print("--------------------------------------------------------------------------------\n");
    //      out.println(
    //              "The above is a list of all the sessions, please enter the serial number of the session you want to choose.");
    //      out.print("--------------------------------------------------------------------------------\n");
    //     out.println();

    //     int index = readPositiveInteger(inputReader);
    //      if (index > size) {
    //          throw new IllegalArgumentException("Invalid input: there is no such a session!");
    //      }
    //      return sessionList.get(index - 1);
    // }

    /**
     * Send email to a given email address.
     */
    // public void sendNotification(Email target, String sub, String body) throws GeneralSecurityException, IOException {
    //     Email eF = new Email("jzsun00@gmail.com");
    //     EmailNotification sender = new EmailNotification(eF, target);
    //     sender.sendEmail(sub, body);
    // }

    /**
     * Send notification to student when their attendance status is changed.
     */
    // public void sendStatusChangeNotification(Student s, Session ses, Status sta)
    //         throws GeneralSecurityException, IOException {
    //     String sub = "Attendance Status Changed";
    //     StringBuilder body = new StringBuilder("To ");
    //     body.append(s.getLegalName());
    //     body.append(": \n");
    //     body.append("\n");
    //     body.append("Notice: Your attendance status on the course: ");
    //     body.append(section.getCourseId());
    //     body.append(" at date: ");
    //     body.append(ses.getSessionDate());
    //     body.append(" has been changed to '");
    //     body.append(sta.getStatus());
    //     body.append("'. If there is any problem, please contact your professor.\n");
    //     body.append("\n");
    //     body.append("ECE 651 team 6");
    //     sendNotification(s.getEmail(), sub, body.toString());
    // }

    /**
     * Full action of changing attendance status of a student.
     */
    // public void changeStatus() throws Exception {
    //     Session target = chooseSession();

    //     Student s = doSearchStudent();

    //     Status newSta = readStatus(
    //             "Please type in the new attendance status of this student. ('p' for present, 'a' for absent, 't' for tardy)\n");

    //     Boolean flag = false;

    //     AttendanceRecord r = attendanceRecordDAO.findAttendanceRecordBySessionAndStudent(target.getSessionId(), s.getUserid());

    //     if (r != null) {
    //         r.setStatus(newSta);
    //         attendanceRecordDAO.updateAttendanceRecord(r);
    //         out.print("--------------------------------------------------------------------------------\n");
    //         out.print("Successfully update the record!\n");
    //         out.print("--------------------------------------------------------------------------------\n");
    //         // to-do
    //         // send email notification
    //         if (enrollmentDAO.findEnrollmentByStudentAndSection(s.getUserid(), section.getSectionId()).isReceiveNotifications()) {
    //             sendStatusChangeNotification(s, target, newSta);
    //         }

    //     } else {
    //         out.print("--------------------------------------------------------------------------------\n");
    //         out.print("Sorry, there is no record of this student in chosen session!\n");
    //         out.print("--------------------------------------------------------------------------------\n");
    //     }
    // }


    /**
     * Read export file format from input.
     */
    // public String readFormat(String prompt) throws IOException {
    //     out.print("--------------------------------------------------------------------------------\n");
    //     out.print(prompt);
    //     out.print("--------------------------------------------------------------------------------\n");
    //     out.println();

    //     String s = inputReader.readLine();
    //     if (s == null) {
    //         throw new EOFException(
    //                 "You didn't type in any instruction!\n");
    //     }
    //     if (!(s.equals("xml") || s.equals("json"))) {
    //         throw new IllegalArgumentException("Invalid format! You should only choose json or xml!");
    //     }
    //     return s;
    // }

    private void sendFileToClient(ObjectOutputStream out, String filePath) throws IOException {
        File file = new File(filePath);
        out.writeObject(file.getName());
        byte[] buffer = new byte[4096];
        FileInputStream fileIn = new FileInputStream(file);
        int bytesRead;
        while ((bytesRead = fileIn.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);  // Then send the file content
        }
        fileIn.close();
        out.writeObject("EOF");
        out.flush();
    }
    

    /**
     * Export attendance record of a session.
     */
    public void exportSessions() throws Exception {
        String json = (String) input.readObject();
        HashMap<String, String> command = mapper.readValue(json, HashMap.class);

        String format = command.get("format");
        // String filepath = command.get("path");

        Date date = new Date();
        String path = "src/profExportReport/" + section.getSectionId() + "_" + date.toString() + "." + format;

        ExportService.exportSectionAttendanceDataForProfessor(section.getSectionId(), format, path);
        sendFileToClient(output, path);
        // sendFileService.sendFile(out, path);
    }

    /**
     * Loop of all the actions.
     */
    public void loop() throws Exception {
        boolean flag = true;

        while (flag) {
            try {
                String json = (String) input.readObject();
                Map<String, String> command = mapper.readValue(json, Map.class);

                if ("TakeAttendance".equals(command.get("action"))) {
                    // full_takeAttendance();
                    System.out.println("Server received take attendance.");

                } else if ("ChangeStatus".equals(command.get("action"))) {
                    // changeStatus();
                    System.out.println("Server received change status.");

                } else if ("Export".equals(command.get("action"))) {
                    System.out.println("Server goes to export.");
                    exportSessions();
                    System.out.println("Server finishes export.");
                } else if ("Exit".equals(command.get("action"))) {
                    output.writeObject("You have exited from course: " +
                            section.getCourseId() +
                            " sectioin: "+
                            section.getSectionId() +
                            "!" +
                            "\n");

                    flag = false;
                    break;
                }

            } catch (EOFException e) {
                System.out.println("Client disconnected.");
                flag = false; 
            } catch (Exception e) {
                output.writeObject(e.getMessage());
                output.flush();
            }
            output.flush();
        }
    }

    // public void loop() throws Exception {
    //     boolean flag = true;

    //     while (flag) {
    //         try {
    //             out.print("--------------------------------------------------------------------------------\n");
    //             out.print("1. Start a new session and take attendance.\n" +
    //                     "2. Change attendance record of a student in one session.\n" +
    //                     "3. Export attendance records of a session.\n" +
    //                     "4. Exit this course.\n" +
    //                     "Above are all the available actions. What do you want to do? Please type in the index number:\n");
    //             out.print("--------------------------------------------------------------------------------\n");
    //             out.println();

    //             int index = readPositiveInteger(inputReader);

    //             if (index == 1) {
    //                 full_takeAttendance();
    //             } else if (index == 2) {
    //                 changeStatus();
    //             } else if (index == 3) {
    //                 // todo
    //                 // add export method
    //                 exportSessions();
    //             } else if (index == 4) {
    //                 out.print("--------------------------------------------------------------------------------\n");
    //                 out.print("You have exited from course: " +
    //                         // course.getCourseid() +
    //                         section.getCourseId() +
    //                         section.getSectionId() +
    //                         "!" +
    //                         "\n");
    //                 out.print("--------------------------------------------------------------------------------\n");

    //                 flag = false;
    //                 break;
    //             } else {
    //                 throw new IllegalArgumentException("Invalid action number, please choose your action again!");
    //             }

    //         } catch (Exception e) {
    //             out.println(e.getMessage());
    //         }
    //     }
    // }

    // public BufferedReader getInputReader() {
    //     return inputReader;
    // }
}

