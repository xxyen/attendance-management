package edu.duke.ece651.shared;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

/**
 * Handles file operations for loading and saving data related to courses,
 * students, and professors.
 */
public class FileHandler {
    private static String workingDir = System.getProperty("user.dir");
    private static String DATA_PATH = workingDir + "/data/";
    // private static String DATA_PATH = workingDir + "/shared/data/";

    public static void setDataPath(String path) {
        DATA_PATH = path;
    }

    /**
     * Loads global student data from a JSON file.
     *
     * @return A map of student IDs to Student objects.
     * @throws Exception
     */
    public static Map<String, Student> loadGlobalStudents() throws Exception {
        Map<String, Student> students = new HashMap<>();
        String path = DATA_PATH + "StudentList.json";

        String decryptedPath = path + ".decrypted";
        FileEncryptorDecryptor.decrypt(path, decryptedPath);

        try (FileReader reader = new FileReader(decryptedPath)) {
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
        // new File(decryptedPath).delete();
        return students;
    }

    /**
     * Loads global professor data from a JSON file.
     *
     * @return A map of professor IDs to Professor objects.
     * @throws Exception
     */
    public static Map<String, Professor> loadGlobalProfessors() throws Exception {
        Map<String, Professor> professors = new HashMap<>();
        String path = DATA_PATH + "ProfessorList.json";
        // String decryptedPath = DATA_PATH + "ProfessorListDecrypted.json";
        String decryptedPath = path + ".decrypted";
        FileEncryptorDecryptor.decrypt(path, decryptedPath);

        try (FileReader reader = new FileReader(decryptedPath)) {
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
        // new File(decryptedPath).delete();
        return professors;
    }

    /**
     * Loads the list of items (such as course IDs or session file names) from a
     * manifest file.
     *
     * @param manifestPath Path to the manifest file.
     * @return A list of items read from the manifest file.
     * @throws FileNotFoundException If the manifest file is not found.
     */
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
    /**
     * Loads all courses along with their students, professors, and sessions.
     *
     * @param globalStudents   Map of global student IDs to Student objects.
     * @param globalProfessors Map of global professor IDs to Professor objects.
     * @return A list of Course objects.
     * @throws Exception
     */
    public static List<Course> loadCourses(Map<String, Student> globalStudents,
            Map<String, Professor> globalProfessors) throws Exception {
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

    /**
     * Loads students for a specific course from a JSON file.
     *
     * @param courseId       The ID of the course.
     * @param globalStudents A map of all students by their ID.
     * @return A list of Student objects enrolled in the specified course.
     * @throws Exception
     */
    private static List<Student> loadCourseStudents(String courseId, Map<String, Student> globalStudents)
            throws Exception {
        List<Student> students = new ArrayList<>();
        String path = DATA_PATH + courseId + "/StudentList_" + courseId + ".json";
        String decryptedPath = path + ".decrypted";
        FileEncryptorDecryptor.decrypt(path, decryptedPath);

        try (FileReader reader = new FileReader(decryptedPath)) {
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
        // new File(decryptedPath).delete();
        return students;
    }

    /**
     * Loads professors for a specific course from a JSON file.
     *
     * @param courseId         The ID of the course.
     * @param globalProfessors A map of all professors by their ID.
     * @return A list of Professor objects teaching the specified course.
     * @throws Exception
     */
    private static List<Professor> loadCourseProfessors(String courseId, Map<String, Professor> globalProfessors)
            throws Exception {
        List<Professor> professors = new ArrayList<>();
        String path = DATA_PATH + courseId + "/Professor_" + courseId + ".json";
        String decryptedPath = path + ".decrypted";
        FileEncryptorDecryptor.decrypt(path, decryptedPath);

        try (FileReader reader = new FileReader(decryptedPath)) {
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
        // new File(decryptedPath).delete();
        return professors;
    }

    /**
     * Loads session data for a specific course from files listed in the course's
     * session manifest.
     *
     * @param courseId       The ID of the course.
     * @param globalStudents A map of all students by their ID for attendance record
     *                       lookup.
     * @return A list of Session objects containing attendance records for the
     *         specified course.
     * @throws FileNotFoundException If the manifest file for the sessions is not
     *                               found.
     */
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

    /**
     * Loads attendance records from a session file.
     *
     * @param filePath       The path to the session file.
     * @param globalStudents A map of all students by their ID for populating
     *                       attendance records.
     * @return An ArrayList of AttendanceRecord objects for the session.
     * @throws Exception
     */
    private static ArrayList<AttendanceRecord> loadAttendanceRecords(String filePath,
            Map<String, Student> globalStudents) throws Exception {
        ArrayList<AttendanceRecord> records = new ArrayList<>();

        String decryptedPath = filePath + ".decrypted";
        FileEncryptorDecryptor.decrypt(filePath, decryptedPath);

        try (BufferedReader br = new BufferedReader(new FileReader(decryptedPath))) {
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
        // new File(decryptedPath).delete();
        return records;
    }

    /**
     * Updates or adds a student to the global student list and persists the change
     * to the StudentList.json file.
     *
     * @param student The student to update or add.
     * @throws Exception
     */
    static void updateOrAddStudentInGlobalList(Student student) throws Exception {
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

        String tempPath = DATA_PATH + "StudentList.json" + ".temp";
        try (FileWriter file = new FileWriter(tempPath)) {
            file.write(studentsArray.toString());
        }
        FileEncryptorDecryptor.encrypt(tempPath, DATA_PATH + "StudentList.json");
        // new File(tempPath).delete();
    }

    /**
     * Loads student roster information from a CSV file into the specified Course
     * object, updating the global list of students accordingly.
     *
     * @param courseId   The ID of the course to which the roster belongs.
     * @param course     The Course object to which the students will be added.
     * @param rosterPath The file path to the CSV file containing the roster
     *                   information.
     * @param order      A list specifying the order of columns in the CSV file
     *                   corresponding to student attributes (e.g., studentID,
     *                   legalName, displayName, emailAddr).
     * @param withHeader A boolean indicating whether the CSV file contains a header
     *                   row.
     * @throws Exception
     * @throws FileNotFoundException if the specified CSV file is not found.
     */
    public static void loadRosterFromCSVFile(String courseId, Course course, String rosterPath, List<Integer> order,
            boolean withHeader) throws Exception {
        File rosterFile = new File(rosterPath);
        if (!rosterFile.exists()) {
            throw new FileNotFoundException("Roster file not found at: " + rosterPath);
        }
        Map<String, Student> globalStudents = loadGlobalStudents();

        try (BufferedReader br = new BufferedReader(new FileReader(rosterFile))) {
            String line;
            JSONArray courseStudentsArray = new JSONArray();

            if (withHeader) {
                br.readLine(); // get rid of the header row
            }
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                Student newStudent = new Student(values[order.get(0)], values[order.get(1)], values[order.get(2)],
                        new Email(values[order.get(3)]));
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
            String tempPath = courseStudentListPath + ".temp";
            try (FileWriter file = new FileWriter(tempPath)) {
                file.write(courseStudentsArray.toString());
            }
            FileEncryptorDecryptor.encrypt(tempPath, courseStudentListPath);
            // new File(tempPath).delete();
        }
    }

    //////////////////////////
    // Student file operation
    /////////////////////////
    /**
     * Writes a list of students to a course-specific JSON file.
     *
     * @param studentsInCourse The list of students enrolled in the course.
     * @param courseId         The ID of the course.
     * @throws Exception
     */
    public static void writeStudentsToCourseFile(List<Student> studentsInCourse, String courseId) throws Exception {
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
        String tempPath = studentsFilePath + ".temp";
        try (FileWriter file = new FileWriter(tempPath)) {
            file.write(studentsArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileEncryptorDecryptor.encrypt(tempPath, studentsFilePath);
        // new File(tempPath).delete();
    }

    /**
     * Adds a student to a specific course, updating the global student list and the
     * course-specific student list.
     *
     * @param studentID The ID of the student to add.
     * @param courseId  The ID of the course to which the student is being added.
     * @return The Student object that was added to the course.
     * @throws Exception
     */
    public static Student addStudentToCourse(String studentID, String courseId) throws Exception {
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

    /**
     * Removes a student from a specific course, updating the course-specific
     * student list.
     *
     * @param studentID The ID of the student to remove.
     * @param courseId  The ID of the course from which the student is being
     *                  removed.
     * @throws Exception
     */
    public static void removeStudentFromCourse(String studentID, String courseId) throws Exception {
        List<Student> studentsInCourse = loadCourseStudents(courseId, loadGlobalStudents());
        studentsInCourse.removeIf(s -> s.getPersonalID().equals(studentID));

        writeStudentsToCourseFile(studentsInCourse, courseId);
    }

    /**
     * Updates course enrollment for a student across all courses after a change in
     * the student's information.
     *
     * @param student The updated Student object.
     * @throws Exception
     */
    public static void updateCoursesForStudent(Student student) throws Exception {
        List<Course> courses = loadCourses(loadGlobalStudents(), loadGlobalProfessors());

        for (Course course : courses) {
            boolean isInCourse = course.getStudents().stream()
                    .anyMatch(s -> s.getPersonalID().equals(student.getPersonalID()));
            if (isInCourse) {
                updateStudentInCourse(student, course.getCourseid());
            }
        }
    }

    /**
     * Updates the information for a student in a specific course's student list
     * file.
     *
     * @param student  The Student object with updated information.
     * @param courseId The ID of the course to update.
     * @throws Exception
     */
    private static void updateStudentInCourse(Student student, String courseId) throws Exception {
        List<Student> studentsInCourse = loadCourseStudents(courseId, loadGlobalStudents());

        studentsInCourse = studentsInCourse.stream()
                .map(s -> s.getPersonalID().equals(student.getPersonalID()) ? student : s)
                .collect(Collectors.toList());

        writeStudentsToCourseFile(studentsInCourse, courseId);
    }

    ////////////////////////
    // Course file operation
    ///////////////////////
    /**
     * Creates a new course directory and initializes required files.
     *
     * @param courseId    The ID of the new course.
     * @param professorId The ID of the professor associated with the new course.
     * @throws Exception
     */
    public static void createCourse(String courseId, String professorId) throws Exception {
        try {
            new File(DATA_PATH + "/" + courseId).mkdirs();
            new File(DATA_PATH + "/" + courseId + "/sessions").mkdirs();
            new FileWriter(DATA_PATH + "/" + courseId + "/sessions/manifest.txt", false).close();

            String studentListFilePath = DATA_PATH + "/" + courseId + "/StudentList_" + courseId + ".json";
            String tempPathStu = studentListFilePath + ".temp";
            try (FileWriter file = new FileWriter(tempPathStu, false)) {
                file.write("[]");
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileEncryptorDecryptor.encrypt(tempPathStu, studentListFilePath);
            // new File(tempPath).delete();

            String professorFilePath = DATA_PATH + "/" + courseId + "/Professor_" + courseId + ".json";
            String tempPathProf = professorFilePath + ".temp";
            try (FileWriter writer = new FileWriter(tempPathProf, false)) {
                writer.write("[\"" + professorId + "\"]");
            }
            FileEncryptorDecryptor.encrypt(tempPathProf, professorFilePath);
            // new File(tempPath).delete();
            updateCoursesManifest(courseId, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes a course directory and its contents.
     *
     * @param courseId The ID of the course to delete.
     */
    public static void deleteCourse(String courseId) {
        deleteDirectory(new File(DATA_PATH + "/" + courseId));

        updateCoursesManifest(courseId, false);
    }

    /**
     * Recursively deletes a directory and all of its contents.
     *
     * @param directory The directory to delete.
     */
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

    /**
     * Updates the course manifest file by adding or removing a course ID.
     *
     * @param courseId The ID of the course to add or remove.
     * @param add      A boolean indicating whether to add (true) or remove (false)
     *                 the course ID.
     */
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
    /**
     * Adds a professor to a specific course by updating the course-specific
     * professor list file.
     *
     * @param professorId The ID of the professor to add to the course.
     * @param courseId    The ID of the course to which the professor is being
     *                    added.
     * @throws Exception
     */
    public static void addProfessorToCourse(String professorId, String courseId) throws Exception {
        String path = DATA_PATH + "/" + courseId + "/Professor_" + courseId + ".json";
        File file = new File(path);
        JSONArray professorsArray = new JSONArray();
        String decryptedPath = path + ".decrypted";

        if (file.exists()) {
            String content;
            try {
                FileEncryptorDecryptor.decrypt(path, decryptedPath);
                content = new String(Files.readAllBytes(Paths.get(decryptedPath)));
                professorsArray = new JSONArray(content);
                // new File(decryptedPath).delete();
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

            String tempPath = path + ".temp";
            try (FileWriter writer = new FileWriter(tempPath, false)) {
                writer.write(updatedProfessorsArray.toString());
                FileEncryptorDecryptor.encrypt(tempPath, path);
                // new File(tempPath).delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //////////////////////////
    // sessions file operation
    //////////////////////////
    /**
     * Adds a new session to a course, including creating the session file and
     * updating the course's session manifest.
     *
     * @param courseId    The ID of the course to which the session is being added.
     * @param sessionTime The date and time of the new session.
     */
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
