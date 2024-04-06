package edu.duke.ece651.shared.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.duke.ece651.shared.Email;
import edu.duke.ece651.shared.Professor;
import edu.duke.ece651.shared.Student;
import java.util.Set;

public class FacultyDAO extends BasicDAO<Professor> {
    public int addFaculty(Professor professor) {
        String sql = "INSERT INTO faculty (user_id, password_hash, faculty_name, email) VALUES (?, ?, ?, ?)";
        return update(sql, professor.getUserid(), professor.getPassword(), professor.getName(), professor.getEmail().getEmailAddr());
    }

    public int deleteFaculty(String userid) {
        String sql = "DELETE FROM faculty WHERE user_id = ?";
        return update(sql, userid);
    }

    public int updateFaculty(Professor professor) {
        String sql = "UPDATE faculty SET user_id = ?, password_hash = ?, faculty_name = ?, email = ? WHERE user_id = ?";
        return update(sql, professor.getUserid(), professor.getPassword(), professor.getName(), professor.getEmail().getEmailAddr(), professor.getUserid());
    }

    public Professor queryFacultyById(String userid) {
        String sql = "SELECT user_id, password_hash, faculty_name, email FROM faculty WHERE user_id = ?";
        Map<String, Object> res = querySingleMapped(sql, userid);
        if(res == null) {
            return null;
        }
        return new Professor((String)res.get("user_id"),(String)res.get("password_hash"), (String)res.get("faculty_name"), new Email((String)res.get("email")));
    }

    public Set<Professor> queryAllFaculty() {
        String sql = "SELECT user_id, password_hash, legal_name, display_name, email FROM faculty";
        List<Map<String, Object>> res = queryMultiMapped(sql);
        Set<Professor> facultySet = new HashSet<>();
        if(res == null) {
            return null;
        }
        for(Map<String, Object> map: res) {
            Professor faculty = new Professor((String)map.get("user_id"),(String)map.get("password_hash"), (String)map.get("faculty_name"), new Email((String)map.get("email")));
            facultySet.add(faculty);
        }
        return facultySet;
    }

}
