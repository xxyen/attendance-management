package edu.duke.ece651.server;

import edu.duke.ece651.shared.ExportService;
import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.ReaderUtilities;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.dao.*;
import edu.duke.ece651.shared.model.Enrollment;
import edu.duke.ece651.shared.model.Section;
import edu.duke.ece651.shared.service.AttendanceRecordService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

public class StuTextPlayer {
    private Student student;
    private Section section;
    private final BufferedReader inputReader;
    private final PrintStream out;

    private static FacultyDAO facultyDAO = new FacultyDAO();
    private static StudentDAO studentDAO = new StudentDAO();
    private static GlobalSettingDAO gsDAO = new GlobalSettingDAO();
    private static AttendanceRecordDAO attendanceRecordDAO= new AttendanceRecordDAO();
    private static CourseDAO courseDAO = new CourseDAO();
    private static SectionDAO sectionDAO = new SectionDAO();
    private static EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private static SessionDAO sessionDAO = new SessionDAO();

    public StuTextPlayer(BufferedReader inputReader, PrintStream out, Section section, Student student) {
        this.inputReader = inputReader;
        this.out = out;
        this.section = section;
        this.student = student;
    }



    public void loop(){
        boolean flag = true;

        while (flag) {
            try {
                out.print("--------------------------------------------------------------------------------\n");
                out.print("1. Check and change notification preferences.\n" +
                        "2. Get report of your attendance.\n" +
                        "3. Exit this course.\n" +
                        "Above are all the available actions. What do you want to do? Please type in the index number:\n");
                out.print("--------------------------------------------------------------------------------\n");
                out.println();

                int index = ReaderUtilities.readPositiveInteger(inputReader);

                if (index == 1) {
                    Enrollment e = enrollmentDAO.findEnrollmentByStudentAndSection(student.getUserid(), section.getSectionId());
                    String preference;
                    if (e.isReceiveNotifications()){
                        preference = "Receive";
                    }
                    else {
                        preference = "Not Receive";
                    }

                    out.print("--------------------------------------------------------------------------------\n");
                    out.print("Your notification preferences of course: " +
                            section.getCourseId() +
                            ", sec: " +
                            section.getSectionId() +
                            " is: " +
                            preference +
                            "\n");
                    out.print("--------------------------------------------------------------------------------\n");
                    changePreference(e);
                } else if (index == 2) {
                    getReport();
                } else if (index == 3) {
                    out.print("--------------------------------------------------------------------------------\n");
                    out.print("You have exited from course: " +
                            // course.getCourseid() +
                            section.getCourseId() +
                            section.getSectionId() +
                            "!" +
                            "\n");
                    out.print("--------------------------------------------------------------------------------\n");

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

    private void getReport() throws Exception {
        out.print("--------------------------------------------------------------------------------\n");
        out.print("Do you want a summary or detailed report? (type in 's' for summary or 'd' for detailed)\n");
        out.print("--------------------------------------------------------------------------------\n");
        out.println();

        char choice = ReaderUtilities.readSingleLetter(inputReader);
        
        if (choice == 's' || choice == 'S'){
            AttendanceRecordService s = new AttendanceRecordService();
            out.print("--------------------------------------------------------------------------------\n");
            out.print("Your attendance participation score for course: " +
                    section.getCourseId() + 
                    ", section: " + 
                    section.getSectionId() + 
                    " is : " +
                    s.calculateStudentSectionScore(student.getUserid(), section.getSectionId()) +
                    "\n");
            out.print("--------------------------------------------------------------------------------\n");
        }
        else if (choice == 'd' || choice == 'D'){
            exportReport();
        }
        else {
            throw new IllegalArgumentException("Invalid input: you should only type in s or d!");

        }
        
        
    }

    private void exportReport() throws Exception {
        String format = ReaderUtilities.readFormat("Please choose the format (json or xml): \n", inputReader, out);
        Date date = new Date();
        String path = "src/stuExportReport/" + section.getSectionId() + "_" + date.toString() + "." + format;
        ExportService.exportSectionAttendanceDataForStudent(student.getUserid(), section.getSectionId(), format, path);
        sendFileService.sendFile(out, path);
    }

    private void changePreference(Enrollment e) throws Exception {
        out.print("--------------------------------------------------------------------------------\n");
        out.print("Do you want to change your preference? (type in 'y' for yes or 'n' for no)\n");
        out.print("--------------------------------------------------------------------------------\n");
        out.println();

        char choice = ReaderUtilities.readSingleLetter(inputReader);

        if ((choice == 'y') || (choice == 'Y')) {
            e.setReceiveNotifications(!e.isReceiveNotifications());
            enrollmentDAO.updateEnrollment(e);
        } else if ((choice == 'n') || (choice == 'N')) {

        } else {
            throw new IllegalArgumentException("Invalid input: you should only type in y or n!");
        }
        String preference;
        if (e.isReceiveNotifications()){
            preference = "Receive";
        }
        else {
            preference = "Not Receive";
        }

        out.print("--------------------------------------------------------------------------------\n");
        out.print("Now your notification preferences of course: " +
                section.getCourseId() +
                ", sec: " +
                section.getSectionId() +
                " is: " +
                preference +
                "\n");
        out.print("--------------------------------------------------------------------------------\n");

    }
}
