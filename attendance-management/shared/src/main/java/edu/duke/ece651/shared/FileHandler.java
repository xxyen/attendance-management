package edu.duke.ece651.shared;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// shared/data/
// ├── StudentList.csv
// ├── ProfessorList.csv
// ├── courses_manifest.txt    // Lists all courses
// └── course123/
// │   ├── StudentList_course123.csv
// │   ├── professor_course123.csv
// │   └── sessions/
// │       ├── manifest.txt    // Lists all session files for course123
// │       └── 2024-03-21_22-00.csv

public class FileHandler {
    private static final String workingDir = System.getProperty("user.dir");
    private static final String DATA_PATH = workingDir + "/data/";

    public static Map<String, Student> loadGlobalStudents() throws FileNotFoundException {
        Map<String, Student> students = new HashMap<>();
        // String path = "StudentList.csv";
        // InputStream inputStream =
        // FileHandler.class.getClassLoader().getResourceAsStream(path);
        // if (inputStream == null) {
        // throw new FileNotFoundException("Resource not found: " + path);
        // }
        String path = DATA_PATH + "StudentList.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Assuming CSV format: studentID, legalName, displayName, email
                Student student = new Student(values[0], values[1], values[2], new Email(values[3]));
                students.put(student.getStudentID(), student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public static Map<String, Professor> loadGlobalProfessors() {
        Map<String, Professor> professors = new HashMap<>();
        String path = DATA_PATH + "ProfessorList.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Assuming CSV format: name, professorID, email
                Professor professor = new Professor(values[0], values[1], new Email(values[2]));
                professors.put(professor.getPersonalid(), professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professors;
    }

    private static List<String> loadManifest(String manifestPath) throws FileNotFoundException {
        List<String> items = new ArrayList<>();
        // InputStream inputStream =
        // FileHandler.class.getClassLoader().getResourceAsStream(manifestPath);
        // if (inputStream == null) {
        // throw new FileNotFoundException("Resource not found: " + manifestPath);
        // }
        try (BufferedReader br = new BufferedReader(new FileReader(manifestPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                items.add(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    // Load course-specific data
    public static List<Course> loadCourses(Map<String, Student> globalStudents,
            Map<String, Professor> globalProfessors) throws FileNotFoundException {
        List<Course> courses = new ArrayList<>();
        String path = DATA_PATH + "course_manifest.txt";
        List<String> courseIds = loadManifest(path);
        for (String courseId : courseIds) {
            List<Student> courseStudents = loadCourseStudents(courseId, globalStudents);
            List<Professor> courseProfessors = loadCourseProfessors(courseId, globalProfessors);
            List<Session> sessions = loadSessions(courseId, globalStudents);

            Course course = new Course(courseId, courseProfessors.toArray(new Professor[0]),
                    courseStudents.toArray(new Student[0]), true);
            course.setSessions(sessions);
            courses.add(course);
        }
        return courses;
    }

    private static List<Student> loadCourseStudents(String courseId, Map<String, Student> globalStudents) {
        List<Student> students = new ArrayList<>();
        String path = DATA_PATH + courseId + "/StudentList_" + courseId + ".csv";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Use studentID to fetch the Student object from global list
                Student student = globalStudents.get(values[0]);
                if (student != null) {
                    students.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    private static List<Professor> loadCourseProfessors(String courseId, Map<String, Professor> globalProfessors) {
        List<Professor> professors = new ArrayList<>();
        String path = DATA_PATH + courseId + "/Professor_" + courseId + ".csv";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Use professorID to fetch the Professor object from global list
                Professor professor = globalProfessors.get(values[0]);
                if (professor != null) {
                    professors.add(professor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professors;
    }

    private static List<Session> loadSessions(String courseId, Map<String, Student> globalStudents)
            throws FileNotFoundException {
        List<Session> sessions = new ArrayList<>();
        List<String> sessionFiles = loadManifest(DATA_PATH + courseId + "/sessions/manifest.txt");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm");

        for (String fileName : sessionFiles) {
            try {
                Date sessionDate = format.parse(fileName.replace(".csv", ""));
                Session session = new Session(courseId, sessionDate);
                ArrayList<AttendanceRecord> records = loadAttendanceRecords(
                        DATA_PATH + courseId + "/sessions/" + fileName,
                        globalStudents);
                session.setRecords(records);
                sessions.add(session);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessions;
    }

    private static ArrayList<AttendanceRecord> loadAttendanceRecords(String filePath,
            Map<String, Student> globalStudents) {
        ArrayList<AttendanceRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Use studentID to fetch the Student object from global list
                Student student = globalStudents.get(values[0]);
                Status status = new Status(values[1].charAt(0));
                if (student != null) {
                    AttendanceRecord record = new AttendanceRecord(student, status);
                    records.add(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    private static void updateOrAddStudentInGlobalList(Student student) throws IOException {
        File file = new File(DATA_PATH + "StudentList.csv");
        List<Student> allStudents = new ArrayList<>();
        boolean studentFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(student.getStudentID())) {
                    studentFound = true;
                    allStudents.add(student);
                } else {
                    allStudents.add(new Student(values[0], values[1], values[2], new Email(values[3])));
                }
            }
        }

        if (!studentFound) {
            allStudents.add(student);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Student s : allStudents) {
                String line = s.getStudentID() + "," + s.getLegalName() + "," + s.getDisplayName() + ","
                        + s.getEmailAddr().getEmailAddr();
                bw.write(line);
                bw.newLine();
            }
        }
    }

    // To do this, must make sure the course has been created (the course folder and sessions/manifest.txtexist).
    public static void loadRosterFromCsv(String courseId, Course course, String rosterPath) throws IOException {
        File rosterFile = new File(rosterPath);
        if (!rosterFile.exists()) {
            throw new FileNotFoundException("Roster file not found at: " + rosterPath);
        }

        Map<String, Student> globalStudents = loadGlobalStudents();

        try (BufferedReader br = new BufferedReader(new FileReader(rosterFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Student newStudent = new Student(values[0], values[1], values[2], new Email(values[3]));
                Student existingStudent = globalStudents.get(values[0]);

                if (existingStudent != null) {
                    // Check if the existing student details match the new details
                    if (!existingStudent.equals(newStudent)) {
                        // Update the global list with the new student details
                        updateOrAddStudentInGlobalList(newStudent);
                        globalStudents.put(newStudent.getStudentID(), newStudent);
                    }
                } else {
                    // Student does not exist in global list, add new student
                    updateOrAddStudentInGlobalList(newStudent);
                    globalStudents.put(newStudent.getStudentID(), newStudent);
                }

                course.addStudent(newStudent);
            }
        }

        // Update course-specific student list file
        String courseRosterPath = DATA_PATH + courseId + "/StudentList_" + courseId + ".csv";
        File file = new File(courseRosterPath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Student student : course.getStudents()) {
                String line = student.getStudentID() + "," + student.getLegalName() + "," +
                        student.getDisplayName() + "," + student.getEmailAddr().getEmailAddr();
                bw.write(line);
                bw.newLine();
            }
        }
    }
}
