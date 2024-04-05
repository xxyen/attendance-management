package edu.duke.ece651.shared.dao;

import java.sql.SQLException;
import java.util.List;

import edu.duke.ece651.shared.Student;
import edu.duke.ece651.shared.dao.BasicDAO;

public class StudentDAO extends BasicDAO<Student> {
    public int addStudent(Student student) throws Exception{
        String sql = "INSERT INTO student (user_id, password_hash, legal_name, display_name, email) VALUES (?, ?, ?, ?, ?)";
        return update(sql, student.getUserid(), student.getPassword(), student.getLegalName(), student.getDisplayName(), student.getEmail().getEmailAddr());
    }

    public int deleteStudent(String userid) {
        String sql = "DELETE FROM student WHERE user_id = ?";
        return update(sql, userid);
    }

    public int updateStudent(Student student) {
        String sql = "UPDATE student SET user_id = ?, password_hash = ?, legal_name = ?, display_name = ?, email = ? WHERE user_id = ?";
        return update(sql, student.getUserid(), student.getPassword(), student.getLegalName(), student.getDisplayName(), student.getEmail().getEmailAddr(), student.getUserid());
    }

    public Student queryStudentById(String userid) {
        // String sql = "SELECT * FROM student WHERE user_id = ?";
        String sql = " SELECT user_id AS userid, password_hash AS password, legal_name AS legalName, display_name AS displayName, email AS email FROM student WHERE user_id = ?";

        return querySingle(sql, Student.class, userid);
    }

    public List<Student> queryAllStudent() {
        String sql = "SELECT * FROM student";
        return queryMulti(sql, Student.class);
    }

    public boolean checkStudentExists(String userid) {
        return false;
    }
}
