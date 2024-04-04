package edu.duke.ece651.shared.dao;

import java.util.List;

import edu.duke.ece651.shared.Student;

public class StudentDAO extends BasicDAO<Student> {
    public int addStudent(Student student) {
        String sql = "INSERT INTO student (user_id, password_hash, email, legal_name, display_name) VALUES (?, ?, ?, ?, ?)";
        return update(sql, student.getUserid(), student.getPassword(), student.getEmail().getEmailAddr(), student.getLegalName(), student.getDisplayName());
    }

    public int deleteFaculty(Integer userid) {
        String sql = "DELETE FROM student WHERE user_id = ?";
        return update(sql, userid);
    }

    public int updateFaculty(Student student) {
        String sql = "UPDATE student SET user_id = ?, password_hash = ?, email = ?, legal_name = ?, display_name = ? WHERE user_id = ?";
        return update(sql, student.getUserid(), student.getPassword(), student.getEmail().getEmailAddr(), student.getLegalName(), student.getDisplayName());
    }

    public Student queryFacultyById(Integer userid) {
        String sql = "SELECT * FROM student WHERE user_id = ?";
        return querySingle(sql, Student.class, userid);
    }

    public List<Student> queryAllFaculty() {
        String sql = "SELECT * FROM student";
        return queryMulti(sql, Student.class);
    }
}
