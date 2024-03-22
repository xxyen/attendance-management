package edu.duke.ece651.shared;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// src/main/resources/
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

    public static Map<String, Student> loadGlobalStudents() throws FileNotFoundException {
        Map<String, Student> students = new HashMap<>();
        String path = "StudentList.csv";
        InputStream inputStream = FileHandler.class.getClassLoader().getResourceAsStream(path);
        // InputStream inputStream = FileHandler.class.getResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found: " + path);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
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
        String path = "ProfessorList.csv";
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(FileHandler.class.getClassLoader().getResourceAsStream(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Assuming CSV format: name, professorID, email
                Professor professor = new Professor(values[0], values[1], new Email(values[2]));
                professors.put(professor.getProfessorID(), professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professors;
    }

    private static List<String> loadManifest(String manifestPath) throws FileNotFoundException {
        List<String> items = new ArrayList<>();
        InputStream inputStream = FileHandler.class.getClassLoader().getResourceAsStream(manifestPath);
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found: " + manifestPath);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
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
        List<String> courseIds = loadManifest("course_manifest.txt");
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
        String path = courseId + "/StudentList_" + courseId + ".csv";
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(FileHandler.class.getClassLoader().getResourceAsStream(path)))) {
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
        String path = courseId + "/Professor_" + courseId + ".csv";
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(FileHandler.class.getClassLoader().getResourceAsStream(path)))) {
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
        List<String> sessionFiles = loadManifest(courseId + "/sessions/manifest.txt");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm");

        for (String fileName : sessionFiles) {
            try {
                Date sessionDate = format.parse(fileName.replace(".csv", ""));
                Session session = new Session(courseId, sessionDate);
                List<AttendanceRecord> records = loadAttendanceRecords(courseId + "/sessions/" + fileName,
                        globalStudents);
                session.setRecords(records);
                sessions.add(session);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessions;
    }

    private static List<AttendanceRecord> loadAttendanceRecords(String filePath, Map<String, Student> globalStudents) {
        List<AttendanceRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(FileHandler.class.getClassLoader().getResourceAsStream(filePath)))) {
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
}
