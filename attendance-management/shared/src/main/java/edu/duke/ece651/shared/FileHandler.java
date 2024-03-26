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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
// FileEncryptorDecryptor.encrypt(tempPath, path);
// use the tempPath to write file
// new File(tempPath).delete();

public class FileHandler {
    private static final String workingDir = System.getProperty("user.dir");
    private static final String DATA_PATH = workingDir + "/data/";
    // private static final String DATA_PATH = workingDir + "/shared/data/";

    public static Map<String, Student> loadGlobalStudents() throws FileNotFoundException {
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
        List<Professor> professors = new ArrayList<>();
        String path = DATA_PATH + courseId + "/Professor_" + courseId + ".json";
        try (FileReader reader = new FileReader(path)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray professorsArray = new JSONArray(tokener);

            for (int i = 0; i < professorsArray.length(); i++) {
                String professorId = professorsArray.getString(i); // array of professorIDs
                Professor professor = globalProfessors.get(professorId);
                if (professor != null) {
                    if (!professor.hasCourse(courseId)) {
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

    static void updateOrAddStudentInGlobalList(Student student) throws IOException {
        Map<String, Student> students = loadGlobalStudents();
        students.put(student.getPersonalID(), student);

        JSONArray studentsArray = new JSONArray();
        for (Student s : students.values()) {
            JSONObject studentObj = new JSONObject();
            studentObj.put("studentID", s.getPersonalID());
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

    public static void loadRosterFromCSVFile(String courseId, Course course, String rosterPath, List<Integer> order, boolean withHeader) throws IOException {
        File rosterFile = new File(rosterPath);
        if (!rosterFile.exists()) {
            throw new FileNotFoundException("Roster file not found at: " + rosterPath);
        }
        Map<String, Student> globalStudents = loadGlobalStudents();

        try (BufferedReader br = new BufferedReader(new FileReader(rosterFile))) {
            String line;
            JSONArray courseStudentsArray = new JSONArray();
            
            if(withHeader){
                br.readLine(); // get rid of the header row
            }
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                Student newStudent = new Student(values[order.get(0)], values[order.get(1)], values[order.get(2)], new Email(values[order.get(3)]));
                Student existingStudent = globalStudents.get(values[order.indexOf(1)]);

                if (existingStudent != null) {
                    // Check if the existing student details match the new details
                    if (!existingStudent.equals(newStudent)) {
                        // Update the global list with the new student details
                        updateOrAddStudentInGlobalList(newStudent);
                        globalStudents.put(newStudent.getPersonalID(), newStudent);
                    }
                } else {
                    // Student does not exist in global list, add new student
                    updateOrAddStudentInGlobalList(newStudent);
                    globalStudents.put(newStudent.getPersonalID(), newStudent);
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
                        globalStudents.put(newStudent.getPersonalID(), newStudent);
                    }
                } else {
                    // Student does not exist in global list, add new student
                    updateOrAddStudentInGlobalList(newStudent);
                    globalStudents.put(newStudent.getPersonalID(), newStudent);
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
    }

    //////////////////////////
    // Student file operation
    /////////////////////////
    public static void writeStudentsToCourseFile(List<Student> studentsInCourse, String courseId) {
        JSONArray studentsArray = new JSONArray();
        for (Student student : studentsInCourse) {
            JSONObject studentObj = new JSONObject();
            studentObj.put("studentID", student.getPersonalID());
            studentObj.put("legalName", student.getLegalName());
            studentObj.put("displayName", student.getDisplayName());
            if (student.getEmailAddr() != null) {
                studentObj.put("emailAddr", student.getEmailAddr().getEmailAddr());
            }
            studentsArray.put(studentObj);
        }

        String studentsFilePath = DATA_PATH + courseId + "/StudentList_" + courseId + ".json";
        try (FileWriter file = new FileWriter(studentsFilePath)) {
            file.write(studentsArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Student addStudentToCourse(String studentID, String courseId) throws IOException {
        // if the student is not in gloabal student list, then add it to gloabal student
        // list, and write StudentList.json
        // updateOrAddStudentInGlobalList(student);
        Map<String, Student> students = loadGlobalStudents();
        Student student = students.get(studentID);
        if (student == null) {
            throw new IllegalArgumentException("There is no student with this ID in the school!");
        }

        List<Student> studentsInCourse = loadCourseStudents(courseId, loadGlobalStudents());
        if (studentsInCourse.stream().noneMatch(s -> s.getPersonalID().equals(student.getPersonalID()))) {
            studentsInCourse.add(student);
            writeStudentsToCourseFile(studentsInCourse, courseId);
        } else {
            throw new IllegalArgumentException("The student is already in this course!");
        }
        return student;
    }

    public static void removeStudentFromCourse(String studentID, String courseId) throws IOException {
        List<Student> studentsInCourse = loadCourseStudents(courseId, loadGlobalStudents());
        studentsInCourse.removeIf(s -> s.getPersonalID().equals(studentID));

        writeStudentsToCourseFile(studentsInCourse, courseId);
    }

    public static void updateCoursesForStudent(Student student) throws IOException {
        List<Course> courses = loadCourses(loadGlobalStudents(), loadGlobalProfessors());

        for (Course course : courses) {
            boolean isInCourse = course.getStudents().stream()
                    .anyMatch(s -> s.getPersonalID().equals(student.getPersonalID()));
            if (isInCourse) {
                updateStudentInCourse(student, course.getCourseid());
            }
        }
    }

    private static void updateStudentInCourse(Student student, String courseId) throws IOException {
        List<Student> studentsInCourse = loadCourseStudents(courseId, loadGlobalStudents());

        studentsInCourse = studentsInCourse.stream()
                .map(s -> s.getPersonalID().equals(student.getPersonalID()) ? student : s)
                .collect(Collectors.toList());

        writeStudentsToCourseFile(studentsInCourse, courseId);
    }

    ////////////////////////
    // Course file operation
    ///////////////////////
    public static void createCourse(String courseId, String professorId) {
        try {
            new File(DATA_PATH + "/" + courseId).mkdirs();
            new File(DATA_PATH + "/" + courseId + "/sessions").mkdirs();
            new FileWriter(DATA_PATH + "/" + courseId + "/sessions/manifest.txt", false).close();
            new FileWriter(DATA_PATH + "/" + courseId + "/StudentList_" + courseId + ".json", false).close();
            try (FileWriter writer = new FileWriter(DATA_PATH + "/" + courseId + "/Professor_" + courseId + ".json",
                    false)) {
                writer.write("[\"" + professorId + "\"]");
            }
            updateCoursesManifest(courseId, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCourse(String courseId) {
        deleteDirectory(new File(DATA_PATH + "/" + courseId));

        updateCoursesManifest(courseId, false);
    }

    private static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] entries = directory.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }
        directory.delete();
    }

    private static void updateCoursesManifest(String courseId, boolean add) {
        File manifestFile = new File(DATA_PATH + "/course_manifest.txt");
        List<String> courseIds = new ArrayList<>();

        if (manifestFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(manifestFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        courseIds.add(line.trim());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // add or remove course
        if (add) {
            if (!courseIds.contains(courseId)) {
                courseIds.add(courseId);
            }
        } else {
            courseIds.remove(courseId);
        }
        // write course_manifest.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(manifestFile, false))) {
            for (String id : courseIds) {
                writer.write(id);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////
    // Professor_courseid.json operation
    //////////////////////////////////
    public static void addProfessorToCourse(String professorId, String courseId) {
        String path = DATA_PATH + "/" + courseId + "/Professor_" + courseId + ".json";
        File file = new File(path);
        JSONArray professorsArray = new JSONArray();

        if (file.exists()) {
            String content;
            try {
                content = new String(Files.readAllBytes(Paths.get(path)));
                professorsArray = new JSONArray(content);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        Set<String> professorsSet = new HashSet<>();
        for (int i = 0; i < professorsArray.length(); i++) {
            professorsSet.add(professorsArray.getString(i));
        }

        if (!professorsSet.contains(professorId)) {
            professorsSet.add(professorId);

            JSONArray updatedProfessorsArray = new JSONArray();
            for (String id : professorsSet) {
                updatedProfessorsArray.put(id);
            }

            try (FileWriter writer = new FileWriter(path, false)) {
                writer.write(updatedProfessorsArray.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //////////////////////////
    // sessions file operation
    //////////////////////////
    public static void addSessionToCourse(String courseId, Date sessionTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        String sessionFileName = dateFormat.format(sessionTime) + ".txt";
        String sessionFilePath = DATA_PATH + "/" + courseId + "/sessions/" + sessionFileName;

        try {
            File sessionFile = new File(sessionFilePath);
            sessionFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String manifestPath = DATA_PATH + "/" + courseId + "/sessions/manifest.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(manifestPath, true))) {
            writer.write(sessionFileName);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
