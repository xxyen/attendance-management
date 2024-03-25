package edu.duke.ece651.shared;

import java.io.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TextPlayer {
    private Professor professor;
    private Course course;
    private AttendanceOperator attendance;
    //private Textview textview;
    private FileHandler fileHandler;

    private final BufferedReader inputReader;
    private final PrintStream out;

    public TextPlayer(Professor professor, Course course, AttendanceOperator attendance, FileHandler fileHandler, BufferedReader inputReader, PrintStream out) {
        this.professor = professor;
        this.course = course;
        this.attendance = attendance;
        this.fileHandler = fileHandler;
        this.inputReader = inputReader;
        this.out = out;
    }

    public static char readSingleLetter(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line != null && line.length() == 1 && Character.isLetter(line.charAt(0))) {
            return line.charAt(0);
        } else {
            throw new IllegalArgumentException("Invalid input: you should only type in one letter!");
        }
    }

    public Status readStatus(String prompt) throws IOException{
        out.print("--------------------------------------------------------------------------------\n");
        out.print(prompt);
        out.println("--------------------------------------------------------------------------------\n");
//        String s = inputReader.readLine();
//        if (s == null){
//            throw new EOFException(
//                    "You didn't type in any instruction!\n"
//            );
//        }
        char status = readSingleLetter(inputReader);
        return new Status(status);
    }

    public Session takeAttendance() throws IOException{
        Session newSes = new Session(course.getCourseid(), new Date());
        for (Student s: course.getStudents()){
            out.print("--------------------------------------------------------------------------------\n");
            out.println(s.getDisplayName());
            out.println(s.getPersonalID());
            out.println("--------------------------------------------------------------------------------\n");

            boolean flag = true;
            while (flag) {
                try {
                    Status newStatus = readStatus("Please type in the attendance status of this student. ('p' for present, 'a' for absent, 'l' for late)\n");
                    AttendanceRecord tempR =new AttendanceRecord(s, newStatus);
                    newSes.addRecord(tempR);
                    flag = false;
                } catch (IllegalArgumentException e) {
                    out.println(e.getMessage());
                    //doOnePlacement(s, this.shipCreationFns.get(s));
                }
            }
        }
        return newSes;
    }

    public Student searchStudent(String id){
        for (Student s: course.getStudents()){
            if(s.getPersonalID().equals(id)){
                return s;
            }
        }

        return null;
    }

    public Student doSearchStudent() throws IOException{
        out.print("--------------------------------------------------------------------------------\n");
        out.print("Please type in the student's id:\n");
        out.println("--------------------------------------------------------------------------------\n");
        String s = inputReader.readLine();
        if (s == null){
            throw new EOFException(
                    "You didn't type in any instruction!\n"
            );
        }
        Student ans = searchStudent(s);
        if (ans == null){
            throw new IllegalArgumentException("Invalid input: there is no student with this id!");
        }
        return ans;
    }

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

    public Session chooseSession() throws IOException{
        List<Session> sessionList = course.getSessions();
        int size = sessionList.size();

        for (int i = 0; i < size; i++){
            out.println(Integer.toString(i+1) + ": Date " + sessionList.get(i).getTime());
        }
        out.print("--------------------------------------------------------------------------------\n");
        out.println("The above is a list of all the sessions, please enter the serial number of the session you want to choose.");
        out.println("--------------------------------------------------------------------------------\n");
        int index = readPositiveInteger(inputReader);
        if (index > size){
            throw new IllegalArgumentException("Invalid input: there is no such a session!");
        }
        return sessionList.get(index-1);
    }

    public void changeStatus() throws IOException{
        Session target = chooseSession();

        Student s = doSearchStudent();

        Status newSta = readStatus("Please type in the new attendance status of this student. ('p' for present, 'a' for absent, 'l' for late)\n");

        Boolean flag = false;
        for (AttendanceRecord record: target.getRecords()){
            if (record.getStudent().equals(s)){
                record.changeRecord(newSta);
                flag = true;
                break;
            }
        }

        if (flag == true){
            out.print("--------------------------------------------------------------------------------\n");
            out.print("Successfully update the record!\n");
            out.println("--------------------------------------------------------------------------------\n");
            //to-do
            //send email notification
        }
        else {
            out.print("--------------------------------------------------------------------------------\n");
            out.print("Sorry, there is no record of this student in chosen session!\n");
            out.println("--------------------------------------------------------------------------------\n");
        }
    }

    public void changeDisplayName() throws IOException{
        if (course.isCanChangeName()) {
            Student stu = doSearchStudent();
            out.print("--------------------------------------------------------------------------------\n");
            out.print("Please type in the student's preferred display name:\n");
            out.println("--------------------------------------------------------------------------------\n");
            String s = inputReader.readLine();
            if (s == null) {
                throw new EOFException(
                        "You didn't type in any instruction!\n"
                );
            }

            stu.setDisplayName(s);
            //to-do
            //change display name in src file
        }
        else {
            throw new IllegalArgumentException("Sorry, you cannot change student's display name!");
        }
    }

    public void dropCourse() throws IOException {
        Student stu = doSearchStudent();
        course.removeStudent(stu.getPersonalID());
        out.print("--------------------------------------------------------------------------------\n");
        out.print("Successfully removed the student from the course!\n");
        out.println("--------------------------------------------------------------------------------\n");
        //to-do
        //change student list in src file
    }

    public void addStudent(Student s) throws IOException{
        course.addStudent(s);
        //to-do
        //where to get the new student info: construct a new one or search from main student list?
    }


}
