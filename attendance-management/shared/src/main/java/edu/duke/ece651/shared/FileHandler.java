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

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

// shared/data/
// ├── StudentList.json
// ├── ProfessorList.json
// ├── courses_manifest.txt    // Lists all courses
// └── course123/
// │   ├── StudentList_course123.json
// │   ├── professor_course123.json
// │   └── sessions/
// │       ├── manifest.txt    // Lists all session files for course123
// │       └── 2024-03-21_22-00.txt

// Decrypt file:
// String decryptedPath = path + ".decrypted";
// FileEncryptorDecryptor.decrypt(path, decryptedPath);
// use the decryptedPath to read file
// new File(decryptedPath).delete();

// Encrypt file:
// String tempPath = path + ".temp";
// use the tempPath to write file
// new File(tempPath).delete();

public class FileHandler {
    private static final String workingDir = System.getProperty("user.dir");
    private static final String DATA_PATH = workingDir + "/data/";

    public static Map<String, Student> loadGlobalStudents() throws FileNotFoundException {
        // Map<String, Student> students = new HashMap<>();
        // String path = "StudentList.csv";
        // InputStream inputStream =
        // FileHandler.class.getClassLoader().getResourceAsStream(path);
        // if (inputStream == null) {
        // throw new FileNotFoundException("Resource not found: " + path);
        // }
        // String path = DATA_PATH + "StudentList.csv";
        // try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        // String line;
        // while ((line = br.readLine()) != null) {
        // String[] values = line.split(",");
        // // Assuming CSV format: studentID, legalName, displayName, email
        // Student student = new Student(values[0], values[1], values[2], new
        // Email(values[3]));
        // students.put(student.getStudentID(), student);
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return students;
        Map<String, Student> students = new HashMap<>();
        String path = DATA_PATH + "StudentList.json";
        try (FileReader reader = new FileReader(path)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray studentsArray = new JSONArray(tokener);

            for (int i = 0; i < studentsArray.length(); i++) {
                JSONObject studentObj = studentsArray.getJSONObject(i);
                String studentID = studentObj.getString("studentID");
                String legalName = studentObj.getString("legalName");
                String displayName = studentObj.optString("displayName", null);
                Email emailAddr = studentObj.has("emailAddr") ? new Email(studentObj.getString("emailAddr")) : null;

                Student student = new Student(studentID, legalName, displayName, emailAddr);
                students.put(studentID, student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public static Map<String, Professor> loadGlobalProfessors() {
        // Map<String, Professor> professors = new HashMap<>();
        // String path = DATA_PATH + "ProfessorList.csv";
        // try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        // String line;
        // while ((line = br.readLine()) != null) {
        // String[] values = line.split(",");
        // // Assuming CSV format: name, professorID, email
        // Professor professor = new Professor(values[0], values[1], new
        // Email(values[2]));
        // professors.put(professor.getProfessorID(), professor);
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return professors;
        Map<String, Professor> professors = new HashMap<>();
        String path = DATA_PATH + "ProfessorList.json";
        try (FileReader reader = new FileReader(path)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray professorsArray = new JSONArray(tokener);

            for (int i = 0; i < professorsArray.length(); i++) {
                JSONObject professorObj = professorsArray.getJSONObject(i);
                String name = professorObj.getString("name");
                String professorID = professorObj.getString("professorID");
                Email email = professorObj.has("email") ? new Email(professorObj.getString("email")) : null;

                ArrayList<String> courseids = new ArrayList<>();
                if (professorObj.has("courseids")) {
                    JSONArray courseIdsArray = professorObj.getJSONArray("courseids");
                    for (int j = 0; j < courseIdsArray.length(); j++) {
                        courseids.add(courseIdsArray.getString(j));
                    }
                }

                Professor professor = new Professor(name, professorID, email, courseids);
                professors.put(professorID, professor);
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
        // List<Student> students = new ArrayList<>();
        // String path = DATA_PATH + courseId + "/StudentList_" + courseId + ".csv";
        // try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        // String line;
        // while ((line = br.readLine()) != null) {
        // String[] values = line.split(",");
        // // Use studentID to fetch the Student object from global list
        // Student student = globalStudents.get(values[0]);
        // if (student != null) {
        // students.add(student);
        // }
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return students;
        List<Student> students = new ArrayList<>();
        String path = DATA_PATH + courseId + "/StudentList_" + courseId + ".json";
        try (FileReader reader = new FileReader(path)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray studentsArray = new JSONArray(tokener);

            for (int i = 0; i < studentsArray.length(); i++) {
                JSONObject studentObj = studentsArray.getJSONObject(i);
                String studentID = studentObj.getString("studentID");
                Student student = globalStudents.get(studentID);
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
        // List<Professor> professors = new ArrayList<>();
        // String path = DATA_PATH + courseId + "/Professor_" + courseId + ".csv";
        // try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        // String line;
        // while ((line = br.readLine()) != null) {
        // String[] values = line.split(",");
        // // Use professorID to fetch the Professor object from global list
        // Professor professor = globalProfessors.get(values[0]);
        // if (professor != null) {
        // professor.addCourse(courseId);
        // professors.add(professor);
        // }
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return professors;
        List<Professor> professors = new ArrayList<>();
        String path = DATA_PATH + courseId + "/Professor_" + courseId + ".json";
        try (FileReader reader = new FileReader(path)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray professorsArray = new JSONArray(tokener);

            for (int i = 0; i < professorsArray.length(); i++) {
                String professorId = professorsArray.getString(i); // array of professorIDs
                Professor professor = globalProfessors.get(professorId);
                if (professor != null) {
                    if (!professor.getCourseids().contains(courseId)) {
                        professor.addCourse(courseId);
                    }
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
                Date sessionDate = format.parse(fileName.replace(".txt", ""));
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
        // File file = new File(DATA_PATH + "StudentList.csv");
        // List<Student> allStudents = new ArrayList<>();
        // boolean studentFound = false;

        // try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        // String line;
        // while ((line = br.readLine()) != null) {
        // String[] values = line.split(",");
        // if (values[0].equals(student.getStudentID())) {
        // studentFound = true;
        // allStudents.add(student);
        // } else {
        // allStudents.add(new Student(values[0], values[1], values[2], new
        // Email(values[3])));
        // }
        // }
        // }

        // if (!studentFound) {
        // allStudents.add(student);
        // }

        // try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
        // for (Student s : allStudents) {
        // String line = s.getStudentID() + "," + s.getLegalName() + "," +
        // s.getDisplayName() + ","
        // + s.getEmailAddr().getEmailAddr();
        // bw.write(line);
        // bw.newLine();
        // }
        // }
        Map<String, Student> students = loadGlobalStudents();
        students.put(student.getStudentID(), student);

        JSONArray studentsArray = new JSONArray();
        for (Student s : students.values()) {
            JSONObject studentObj = new JSONObject();
            studentObj.put("studentID", s.getStudentID());
            studentObj.put("legalName", s.getLegalName());
            studentObj.put("displayName", s.getDisplayName());
            if (s.getEmailAddr() != null) {
                studentObj.put("emailAddr", s.getEmailAddr().getEmailAddr());
            }
            studentsArray.put(studentObj);
        }

        try (FileWriter file = new FileWriter(DATA_PATH + "StudentList.json")) {
            file.write(studentsArray.toString());
        }
    }

    // To do this, must make sure the course has been created (the course folder and
    // sessions/manifest.txtexist).
    public static void loadRosterFromCsv(String courseId, Course course, String rosterPath) throws IOException {
        File rosterFile = new File(rosterPath);
        if (!rosterFile.exists()) {
            throw new FileNotFoundException("Roster file not found at: " + rosterPath);
        }

        Map<String, Student> globalStudents = loadGlobalStudents();

        try (BufferedReader br = new BufferedReader(new FileReader(rosterFile))) {
            String line;
            JSONArray courseStudentsArray = new JSONArray();
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

                JSONObject studentObj = new JSONObject();
                studentObj.put("studentID", values[0]);
                studentObj.put("legalName", values[1]);
                studentObj.put("displayName", values[2]);
                if (values[3] != null) {
                    studentObj.put("emailAddr", newStudent.getEmailAddr().getEmailAddr());
                }
                courseStudentsArray.put(studentObj);
            }
            // Write to the course-specific StudentList_courseid.json
            String courseStudentListPath = DATA_PATH + courseId + "/StudentList_" + courseId + ".json";
            try (FileWriter file = new FileWriter(courseStudentListPath)) {
                file.write(courseStudentsArray.toString());
            }
        }

        // Update course-specific student list file
        // String courseRosterPath = DATA_PATH + courseId + "/StudentList_" + courseId +
        // ".csv";
        // File file = new File(courseRosterPath);
        // try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
        // for (Student student : course.getStudents()) {
        // String line = student.getStudentID() + "," + student.getLegalName() + "," +
        // student.getDisplayName() + "," + student.getEmailAddr().getEmailAddr();
        // bw.write(line);
        // bw.newLine();
        // }
        // }
    }
}
