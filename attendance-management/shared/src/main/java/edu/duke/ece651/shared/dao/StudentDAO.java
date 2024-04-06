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

public class StudentDAO extends BasicDAO<Student> {
    public int addStudent(Student student) {
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
        //SELECT user_id AS userid, password_hash AS password, legal_name AS legalName, display_name AS displayName, email AS email FROM student WHERE user_id = ?
        String sql = " SELECT user_id, password_hash, legal_name, display_name, email FROM student WHERE user_id = ?";
        Map<String, Object> res = querySingleMapped(sql, userid);
        if(res == null) {
            return null;
        }
        return new Student((String)res.get("user_id"),(String)res.get("password_hash"), (String)res.get("legal_name"), (String)res.get("display_name"), new Email((String)res.get("email")));
    }

    public Set<Student> queryAllStudents() {
        String sql = "SELECT user_id, password_hash, legal_name, display_name, email FROM student";
        List<Map<String, Object>> res = queryMultiMapped(sql);
        Set<Student> studentSet = new HashSet<>();
        if(res == null) {
            return null;
        }
        for(Map<String, Object> map: res) {
            Student student = new Student((String)map.get("user_id"),(String)map.get("password_hash"), (String)map.get("legal_name"), (String)map.get("display_name"), new Email((String)map.get("email")));
            studentSet.add(student);
        }
        return studentSet;
    }

}
