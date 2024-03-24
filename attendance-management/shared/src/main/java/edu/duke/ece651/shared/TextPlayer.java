package edu.duke.ece651.shared;

import java.io.*;
import java.time.LocalDate;
import java.util.Date;

public class TextPlayer {
    private Professor professor;
    private Course course;
    private AttendanceOperator attendance;
    //private Textview textview;
    private FileHandler fileHandler;

  private ExportService export;
  
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
            out.println(s.getStudentID());
            out.println("--------------------------------------------------------------------------------\n");

            boolean flag = true;
            while (flag) {
                try {
                    Status newStatus = readStatus("Please type in the attendance status of this student. ('p' for present, 'a' for absent)\n");
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

  ////////////////////////////////////////////////////////////////////
  public void exportData() throws IOException {
    export.exportToFile(course.getSessions(), "JSON", null, null);
  }
  ///////////////////////////////////////////////////////////////////

}
