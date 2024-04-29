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
    private Section section;

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
     * Take attendance record for all the student of this course.
     * @return a Session of the records
     */
    public Session takeAttendance(Date time) throws IOException,ClassNotFoundException,Exception {
         Session newSes = new Session(section.getSectionId(), time, new Time(time.getTime()), new Time(time.getTime()));
         sessionDAO.addSession(newSes);
         // Send student id list to client
         getStudentIdsForSession(newSes);
        //Receive attendance record form client
        String jsonRecord = (String) input.readObject();
        Map<String, String> attendanceData = mapper.readValue(jsonRecord, Map.class);
        for (Map.Entry<String, String> entry : attendanceData.entrySet()) {
            String studentId = entry.getKey();
            String status = entry.getValue();
            Status newSta = new Status(status.charAt(0));
    
            AttendanceRecord tempR = new AttendanceRecord(newSes.getSessionId(), studentId, newSta);
            attendanceRecordDAO.addAttendanceRecord(tempR);
        }
        return newSes;
    }


    private static Date convertStringToDate(String inputDate) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter.setLenient(false); 
        return formatter.parse(inputDate);
    }


    /**
     * Full action of take attendance of a new session.
     */
    public void full_takeAttendance() throws Exception {
        String json = (String) input.readObject();
        Map<String, String> command = mapper.readValue(json, Map.class);

        if ("TakeAttendanceToday".equals(command.get("action"))) {
            System.out.println("Server starts take attendance for today.");
            takeAttendance(new Date());
            System.out.println("Server finishes take attendance for tody.");

        } else if ("TakeAttendancePreviousDate".equals(command.get("action"))) {
            System.out.println("Server starts take attendance for previous date.");
            Map<String, String> attendanceData = new HashMap<>();
            String jsonData = (String) input.readObject();
            attendanceData = mapper.readValue(jsonData, Map.class);
            String dateString = attendanceData.get("date");
            Date date = convertStringToDate(dateString);
            takeAttendance(date);
            System.out.println("Server finishes take attendance for previous date.");

        } 
    }

    /**
     * Search student with given ID.
     * @param id is the student's ID.
     */
    public Student searchStudent(String id) {
        //todo
        //search student by id in the section
        //return studentDAO.queryStudentById(id);
        Enrollment e = enrollmentDAO.findEnrollmentByStudentAndSection(id, section.getSectionId());
        if (e == null){
            return null;
        }
        return studentDAO.queryStudentById(e.getStudentId());
    }

    public List<String> getStudentIdsForSession(Session s) throws Exception{
        List<Enrollment> enrollments = enrollmentDAO.listEnrollmentsBySection(s.getSectionId());
        List<String> studentIds = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            studentIds.add(enrollment.getStudentId());
        }

        ObjectMapper mapper = new ObjectMapper();
        String studentIdJson = mapper.writeValueAsString(studentIds);
        output.writeObject(studentIdJson);
        output.flush(); 

        return studentIds;
    }

    /**
     * Choose a session from the list.
     */
    public Session chooseSession() throws IOException,ClassNotFoundException {
        List<Session> sessionList = sessionDAO.listSessionsBySection(section.getSectionId());

        ObjectMapper mapper = new ObjectMapper();
        String sessionJson = mapper.writeValueAsString(sessionList);
        output.writeObject(sessionJson);
        output.flush(); 


        int index = 1;
        HashMap<String, Object> command = new ObjectMapper().readValue((String)input.readObject(), HashMap.class);
        if ("SelectSession".equals(command.get("action"))) {
            index = (Integer) command.get("index");
            System.out.println("Server received selected session index: " + index);
        }

        System.out.println("Server go to selected session date: " + sessionList.get(index).getSessionDate());
        return sessionList.get(index);
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
        body.append(section.getCourseId());
        body.append(" at date: ");
        body.append(ses.getSessionDate());
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
        System.out.println("server finishes choose session");

        getStudentIdsForSession(target);
        System.out.println("server finishes send student ids");

        String jsonRecord = (String) input.readObject();
        Map<String, String> record = mapper.readValue(jsonRecord, Map.class);

        Student s = searchStudent(record.get("studentid"));

        Boolean flag = false;

        AttendanceRecord r = attendanceRecordDAO.findAttendanceRecordBySessionAndStudent(target.getSessionId(), record.get("studentid"));

        Status newSta = new Status(record.get("status").charAt(0));
        if (r != null) {
            r.setStatus(newSta);
            attendanceRecordDAO.updateAttendanceRecord(r);
            output.writeObject("success");
            output.flush();
            if (enrollmentDAO.findEnrollmentByStudentAndSection(record.get("studentid"), section.getSectionId()).isReceiveNotifications()) {
                sendStatusChangeNotification(s, target, newSta);
            }

        } else {
            output.writeObject("Sorry, there is no record of this student in chosen session!\n");
            output.flush();
        }
    }

    private void sendFileToClient(ObjectOutputStream output, String filePath) throws IOException {
        File file = new File(filePath);
        output.writeObject(file.getName());
    
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            output.writeObject(line);
        }
        reader.close();
    
        output.writeObject("EOF");
        output.flush();
    }
    
    /**
     * Export attendance record of a session.
     */
    public void exportSessions() throws Exception {
        String json = (String) input.readObject();
        HashMap<String, String> command = mapper.readValue(json, HashMap.class);

        String format = command.get("format");

        Date date = new Date();
        String path = "src/profExportReport/" + section.getSectionId() + "_" + date.toString() + "." + format;

        ExportService.exportSectionAttendanceDataForProfessor(section.getSectionId(), format, path);
        sendFileToClient(output, path);
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
                    System.out.println("Server starts take attendance.");
                    full_takeAttendance();
                    System.out.println("Server finishes take attendance.");

                } else if ("ChangeStatus".equals(command.get("action"))) {
                    System.out.println("Server starts change status.");
                    changeStatus();
                    System.out.println("Server finishes change status.");

                } else if ("Export".equals(command.get("action"))) {
                    System.out.println("Server starts export.");
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
}

