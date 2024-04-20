package edu.duke.ece651.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.duke.ece651.shared.Email;
import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.dao.BasicDAO;
import edu.duke.ece651.shared.dao.SectionDAO;

/**
 * The StudentDAO class provides data access operations specific to students.
 * It extends the BasicDAO class and inherits basic data access methods.
 */
public class StudentDAO extends BasicDAO<Student> {
    /**
     * Adds a new student to the database.
     *
     * @param student the Student object representing the student to be added
     * @return the number of rows affected by the insert operation
     */
    public int addStudent(Student student) {
        String sql = "INSERT INTO student (user_id, password_hash, legal_name, display_name, email) VALUES (?, ?, ?, ?, ?)";
        return update(sql, student.getUserid(), student.getPassword(), student.getLegalName(), student.getDisplayName(), student.getEmail().getEmailAddr());
    }

    /**
     * Deletes a student from the database based on their user ID.
     *
     * @param userid the user ID of the student to be deleted
     * @return the number of rows affected by the delete operation
     */
    public int deleteStudent(String userid) {
        String sql = "DELETE FROM student WHERE user_id = ?";
        return update(sql, userid);
    }

    /**
     * Updates information for an existing student in the database.
     *
     * @param student the Student object representing the updated information
     * @return the number of rows affected by the update operation
     */
    public int updateStudent(Student student) {
        String sql = "UPDATE student SET user_id = ?, password_hash = ?, legal_name = ?, display_name = ?, email = ? WHERE user_id = ?";
        return update(sql, student.getUserid(), student.getPassword(), student.getLegalName(), student.getDisplayName(), student.getEmail().getEmailAddr(), student.getUserid());
    }

    /**
     * Retrieves information for a student based on their user ID.
     *
     * @param userid the user ID of the student to query
     * @return a Student object representing the queried student, or null if not found
     */
    public Student queryStudentById(String userid) {
        //SELECT user_id AS userid, password_hash AS password, legal_name AS legalName, display_name AS displayName, email AS email FROM student WHERE user_id = ?
        String sql = " SELECT user_id, password_hash, legal_name, display_name, email FROM student WHERE user_id = ?";
        Map<String, Object> res = querySingleMapped(sql, userid);
        if(res == null) {
            return null;
        }
        return new Student((String)res.get("user_id"),(String)res.get("password_hash"), (String)res.get("legal_name"), (String)res.get("display_name"), new Email((String)res.get("email")));
    }

    /**
     * Retrieves information for a student based on their email address.
     *
     * @param email the email address of the student to query
     * @return a Student object representing the queried student, or null if not found
     */
    public Student queryStudentByEmail(String email) {
        String sql = " SELECT user_id, password_hash, legal_name, display_name, email FROM student WHERE email = ?";
        Map<String, Object> res = querySingleMapped(sql, email);
        if(res == null) {
            return null;
        }
        return new Student((String)res.get("user_id"),(String)res.get("password_hash"), (String)res.get("legal_name"), (String)res.get("display_name"), new Email((String)res.get("email")));
    }

    /**
     * Retrieves a list of students with the given legal name.
     *
     * @param legalName the legal name of the students to query
     * @return a list of Student objects representing the queried students
     */
    public List<Student> queryStudentByLegalName(String legalName) {
        String sql = " SELECT user_id, password_hash, legal_name, display_name, email FROM student WHERE legal_name = ?";
        List<Map<String, Object>> res = queryMultiMapped(sql, legalName);
        List<Student> studentList = new ArrayList<>();
        // if(res == null) {
        //     return null;
        // }
        for(Map<String, Object> map: res) {
            Student student = new Student((String)map.get("user_id"),(String)map.get("password_hash"), (String)map.get("legal_name"), (String)map.get("display_name"), new Email((String)map.get("email")));
            studentList.add(student);
        }
        return studentList;
    }

    /**
     * Retrieves information for all students.
     *
     * @return a set of Student objects representing all students in the database
     */
    public Set<Student> queryAllStudents() {
        String sql = "SELECT user_id, password_hash, legal_name, display_name, email FROM student";
        List<Map<String, Object>> res = queryMultiMapped(sql);
        Set<Student> studentSet = new HashSet<>();
        // if(res == null) {
        //     return null;
        // }
        for(Map<String, Object> map: res) {
            Student student = new Student((String)map.get("user_id"),(String)map.get("password_hash"), (String)map.get("legal_name"), (String)map.get("display_name"), new Email((String)map.get("email")));
            studentSet.add(student);
        }
        return studentSet;
    }

}
