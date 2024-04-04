package edu.duke.ece651.shared.dao;

import java.util.List;

import edu.duke.ece651.shared.Professor;

public class FacultyDAO extends BasicDAO<Professor> {
    public int addFaculty(Professor professor) {
        String sql = "INSERT INTO faculty (user_id, password_hash, email, faculty_name) VALUES (?, ?, ?, ?)";
        return update(sql, professor.getUserid(), professor.getPassword(), professor.getEmail().getEmailAddr(), professor.getName());
    }

    public int deleteFaculty(Integer userid) {
        String sql = "DELETE FROM faculty WHERE user_id = ?";
        return update(sql, userid);
    }

    public int updateFaculty(Professor professor) {
        String sql = "UPDATE faculty SET user_id = ?, password_hash = ?, email = ?, faculty_name = ? WHERE user_id = ?";
        return update(sql, professor.getUserid(), professor.getPassword(), professor.getEmail().getEmailAddr(), professor.getName());
    }

    public Professor queryFacultyById(Integer userid) {
        String sql = "SELECT * FROM faculty WHERE user_id = ?";
        return querySingle(sql, Professor.class, userid);
    }

    public List<Professor> queryAllFaculty() {
        String sql = "SELECT * FROM faculty";
        return queryMulti(sql, Professor.class);
    }
}
