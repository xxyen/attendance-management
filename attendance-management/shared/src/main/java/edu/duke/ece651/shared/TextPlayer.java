package edu.duke.ece651.shared;

import java.io.*;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used to display information to user
 * and take action from user.
 * @author Can Pei
 * @version 1.0
 */
public class TextPlayer {
    private Professor professor;
    private Course course;
    private AttendanceOperator attendance;
    // private Textview textview;
    // private FileHandler fileHandler;

    private final BufferedReader inputReader;
    private final PrintStream out;

    private final int actNums;

    /**
     * Constructor
     */
    public TextPlayer(Professor professor, Course course, AttendanceOperator attendance, BufferedReader inputReader,
            PrintStream out) {
        this.professor = professor;
        this.course = course;
        this.attendance = attendance;
        // this.fileHandler = fileHandler;
        this.inputReader = inputReader;
        this.out = out;

        // need to change if number of actions increases
        this.actNums = 7;
    }

    public BufferedReader getInputReader() {
        return inputReader;
    }

    /**
     * Read one single letter from input,
     * used for readStatus.
     */

    public static char readSingleLetter(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line != null && line.length() == 1 && Character.isLetter(line.charAt(0))) {
            return line.charAt(0);
        } else {
            throw new IllegalArgumentException("Invalid input: you should only type in one letter!");
        }
    }

    /**
     * Read a status from input.
     */
    public Status readStatus(String prompt) throws IOException {
        out.print("--------------------------------------------------------------------------------\n");
        out.print(prompt);
        out.println("--------------------------------------------------------------------------------\n");
        // String s = inputReader.readLine();
        // if (s == null){
        // throw new EOFException(
        // "You didn't type in any instruction!\n"
        // );
        // }
        char status = readSingleLetter(inputReader);
        return new Status(status);
    }

    /**
     * Take attendance record for all the student of this course.
     * @return a Session of the records
     */
    public Session takeAttendance() throws IOException {
        Session newSes = new Session(course.getCourseid(), new Date());
        for (Student s : course.getStudents()) {
            out.print("--------------------------------------------------------------------------------\n");
            out.println(s.getDisplayName());
            out.println(s.getUserid());
            out.println("--------------------------------------------------------------------------------\n");

            boolean flag = true;
            while (flag) {
                try {
                    Status newStatus = readStatus(
                            "Please type in the attendance status of this student. ('p' for present, 'a' for absent, 'l' for late)\n");
                    AttendanceRecord tempR = new AttendanceRecord(s, newStatus);
                    newSes.addRecord(tempR);
                    flag = false;
                } catch (IllegalArgumentException e) {
                    out.println(e.getMessage());
                    // doOnePlacement(s, this.shipCreationFns.get(s));
                }
            }
        }
        return newSes;
    }

    /**
     * Add a new session to the course.
     */
    public void addSession(Session s) throws Exception {
        course.addSession(s);
        // to-do
        // update src file: add a new session file
        // FileHandler.addSessionToCourse(course.getCourseid(), s.getTime());
        s.saveAttendanceRecords();
    }

    /**
     * Full action of take attendance of a new session.
     */
    public void full_takeAttendance() throws Exception {
        Session s = takeAttendance();
        addSession(s);

    }

    /**
     * Search student with given ID.
     * @param id is the student's ID.
     */
    public Student searchStudent(String id) {
        for (Student s : course.getStudents()) {
            if (s.getUserid().equals(id)) {
                return s;
            }
        }

        return null;
    }

    /**
     * Full action of searching a student.
     */
    public Student doSearchStudent() throws IOException {
        out.print("--------------------------------------------------------------------------------\n");
        out.print("Please type in the student's id:\n");
        out.println("--------------------------------------------------------------------------------\n");
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException(
                    "You didn't type in any instruction!\n");
        }
        Student ans = searchStudent(s);
        if (ans == null) {
            throw new IllegalArgumentException("Invalid input: there is no student with this id in this course!");
        }
        return ans;
    }

    /**
     * Read a positive integer from input.
     * Used for choosing action or session.
     */
    public int readPositiveInteger(BufferedReader reader) throws IOException {
        String line = reader.readLine(); // 从BufferedReader读取一行
        try {
            int number = Integer.parseInt(line); // 尝试将读取的行转换为整数
            if (number > 0) {
                return number; // 如果是正整数，则返回
            } else {
                throw new IllegalArgumentException("Invalid input: it is not a positive integer!"); // 如果不是正整数，抛出异常
            }
        } catch (NumberFormatException e) {
            // 如果转换失败（即输入不是整数），也抛出IllegalArgumentException
            throw new IllegalArgumentException("Invalid input: it is not a positive integer!");
        }
    }

    /**
     * Choose a session from the list.
     */
    public Session chooseSession() throws IOException {
        List<Session> sessionList = course.getSessions();
        int size = sessionList.size();

        for (int i = 0; i < size; i++) {
            out.println(Integer.toString(i + 1) + ": Date " + sessionList.get(i).getTime());
        }
        out.print("--------------------------------------------------------------------------------\n");
        out.println(
                "The above is a list of all the sessions, please enter the serial number of the session you want to choose.");
        out.println("--------------------------------------------------------------------------------\n");
        int index = readPositiveInteger(inputReader);
        if (index > size) {
            throw new IllegalArgumentException("Invalid input: there is no such a session!");
        }
        return sessionList.get(index - 1);
    }

    /**
     * Send email to a given email address.
     */
    public void sendNotification(Email target, String sub, String body) throws GeneralSecurityException, IOException {
        Email eF = new Email("jzsun00@gmail.com");
        EmailNotification sender = new EmailNotification(eF, target);
        sender.sendEmail(sub, body);
    }

    /**
     * Send notification to student when their attendance status is changed.
     */
    public void sendStatusChangeNotification(Student s, Session ses, Status sta)
            throws GeneralSecurityException, IOException {
        String sub = "Attendance Status Changed";
        StringBuilder body = new StringBuilder("To ");
        body.append(s.getLegalName());
        body.append(": \n");
        body.append("\n");
        body.append("Notice: Your attendance status on the course: ");
        body.append(course.getCourseid());
        body.append(" at date: ");
        body.append(ses.getTime());
        body.append(" has been changed to '");
        body.append(sta.getStatus());
        body.append("'. If there is any problem, please contact your professor.\n");
        body.append("\n");
        body.append("ECE 651 team 6");
        sendNotification(s.getEmail(), sub, body.toString());
    }

    /**
     * Full action of changing attendance status of a student.
     */
    public void changeStatus() throws Exception {
        Session target = chooseSession();

        Student s = doSearchStudent();

        Status newSta = readStatus(
                "Please type in the new attendance status of this student. ('p' for present, 'a' for absent, 'l' for late)\n");

        Boolean flag = false;
        for (AttendanceRecord record : target.getRecords()) {
            if (record.getStudent().equals(s)) {
                record.changeRecord(newSta);
                target.saveAttendanceRecords();
                flag = true;
                break;
            }
        }

        if (flag == true) {
            out.print("--------------------------------------------------------------------------------\n");
            out.print("Successfully update the record!\n");
            out.println("--------------------------------------------------------------------------------\n");
            // to-do
            // send email notification
            sendStatusChangeNotification(s, target, newSta);

        } else {
            out.print("--------------------------------------------------------------------------------\n");
            out.print("Sorry, there is no record of this student in chosen session!\n");
            out.println("--------------------------------------------------------------------------------\n");
        }
    }

    /**
     * Change display name of a student.
     */
    public void changeDisplayName() throws Exception {
        if (course.isCanChangeName()) {
            Student stu = doSearchStudent();
            out.print("--------------------------------------------------------------------------------\n");
            out.print("Please type in the student's preferred display name:\n");
            out.println("--------------------------------------------------------------------------------\n");
            String s = inputReader.readLine();
            if (s == null) {
                throw new EOFException(
                        "You didn't type in any instruction!\n");
            }

            stu.setDisplayName(s);
            // to-do
            // change display name in src file
            // FileHandler.updateCoursesForStudent(stu);
            // FileHandler.updateOrAddStudentInGlobalList(stu);
        } else {
            throw new IllegalArgumentException("Sorry, you cannot change student's display name!");
        }
    }

    /**
     * Remove a student from the course.
     */
    public void removeStudent() throws Exception {
        Student stu = doSearchStudent();
        course.removeStudent(stu.getUserid());
        out.print("--------------------------------------------------------------------------------\n");
        out.print("Successfully removed the student from the course!\n");
        out.println("--------------------------------------------------------------------------------\n");
        // to-do
        // change student list in src file
        // FileHandler.removeStudentFromCourse(stu.getUserid(), course.getCourseid());
    }

    /**
     * Get the student list of the course.
     */
    public List<Student> getStudent() {
        return course.getStudents();
    }
    // public List<Session> getSession(){
    // return course.getSessions();
    // }

    /**
     * Add a new student to the course.
     */
    public void addStudent() throws Exception {
        out.print("--------------------------------------------------------------------------------\n");
        out.print("Please type in the student's id:\n");
        out.println("--------------------------------------------------------------------------------\n");
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException(
                    "You didn't type in any instruction!\n");
        }
        // Student stu = FileHandler.addStudentToCourse(s, course.getCourseid());
        //course.addStudent(stu);
        out.print("--------------------------------------------------------------------------------\n");
        out.print("Successfully added the student to the course!\n");
        out.println("--------------------------------------------------------------------------------\n");

        // to-do
        // where to get the new student info: construct a new one or search from main
        // student list?

    }

    /**
     * Read export file format from input.
     */
    public String readFormat(String prompt) throws IOException {
        out.print("--------------------------------------------------------------------------------\n");
        out.print(prompt);
        out.println("--------------------------------------------------------------------------------\n");
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException(
                    "You didn't type in any instruction!\n");
        }
        if (!(s.equals("xml") || s.equals("json"))) {
            throw new IllegalArgumentException("Invalid format! You should only choose json or xml!");
        }
        return s;
    }

    /**
     * Export attendance record of a session.
     */
    public void exportSessions() throws IOException {
        String format = readFormat("Please choose the format (json or xml): \n");
        out.print("--------------------------------------------------------------------------------\n");
        out.print("Please type in the file path that you want to export to (including file name): \n");
        out.println("--------------------------------------------------------------------------------\n");
        String path = inputReader.readLine();
        List<String> fields = new ArrayList<>();
        fields.add("studentID");
        fields.add("legalName");
        fields.add("displayName");
        fields.add("email");
        fields.add("status");

        ExportService.exportToFile(course.getSessions(), format, fields, path);
        out.print("--------------------------------------------------------------------------------\n");
        out.print("Successfully exported course attendance records!\n");
        out.println("--------------------------------------------------------------------------------\n");

    }

    /**
     * Loop of all the actions.
     */
    public void loop() throws Exception {
        boolean flag = true;

        while (flag) {
            try {
                out.print("--------------------------------------------------------------------------------\n");
                out.print("1. Start a new session and take attendance.\n" +
                        "2. Change attendance record of a student in one session.\n" +
                        "3. Change display name of a student.\n" +
                        "4. Add a student to this course.\n" +
                        "5. Remove a student from this course.\n" +
                        "6. Export attendance records of a session.\n" +
                        "7. Exit this course.\n" +
                        "Above are all the available actions. What do you want to do? Please type in the index number:\n");
                out.println("--------------------------------------------------------------------------------\n");

                int index = readPositiveInteger(inputReader);

                if (index == 1) {
                    full_takeAttendance();
                } else if (index == 2) {
                    changeStatus();
                } else if (index == 3) {
                    changeDisplayName();
                } else if (index == 4) {
                    addStudent();
                } else if (index == 5) {
                    removeStudent();
                } else if (index == 6) {
                    // todo
                    // add export method
                    exportSessions();
                } else if (index == 7) {
                    out.print("--------------------------------------------------------------------------------\n");
                    out.print("You have exited from course: " +
                            course.getCourseid() +
                            "!" +
                            "\n");
                    out.println("--------------------------------------------------------------------------------\n");

                    flag = false;
                    break;
                } else {
                    throw new IllegalArgumentException("Invalid action number, please choose your action again!");
                }

            } catch (Exception e) {
                out.println(e.getMessage());
            }
        }
    }

}
