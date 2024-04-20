package edu.duke.ece651.shared.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.duke.ece651.shared.Email;
import edu.duke.ece651.shared.Professor;
import java.util.Set;

/**
 * The FacultyDAO class provides data access operations specific to faculty members.
 * It extends the BasicDAO class and inherits basic data access methods.
 */
public class FacultyDAO extends BasicDAO<Professor> {

    /**
     * Adds a new faculty member to the database.
     *
     * @param professor the Professor object representing the faculty member to be added
     * @return the number of rows affected by the insert operation
     */
    public int addFaculty(Professor professor) {
        String sql = "INSERT INTO faculty (user_id, password_hash, faculty_name, email) VALUES (?, ?, ?, ?)";
        return update(sql, professor.getUserid(), professor.getPassword(), professor.getName(), professor.getEmail().getEmailAddr());
    }

    /**
     * Deletes a faculty member from the database based on their user ID.
     *
     * @param userid the user ID of the faculty member to be deleted
     * @return the number of rows affected by the delete operation
     */
    public int deleteFaculty(String userid) {
        String sql = "DELETE FROM faculty WHERE user_id = ?";
        return update(sql, userid);
    }

    /**
     * Updates information for an existing faculty member in the database.
     *
     * @param professor the Professor object representing the updated information
     * @return the number of rows affected by the update operation
     */
    public int updateFaculty(Professor professor) {
        String sql = "UPDATE faculty SET user_id = ?, password_hash = ?, faculty_name = ?, email = ? WHERE user_id = ?";
        return update(sql, professor.getUserid(), professor.getPassword(), professor.getName(), professor.getEmail().getEmailAddr(), professor.getUserid());
    }

    /**
     * Retrieves information for a faculty member based on their user ID.
     *
     * @param userid the user ID of the faculty member to query
     * @return a Professor object representing the queried faculty member, or null if not found
     */
    public Professor queryFacultyById(String userid) {
        //System.out.println("test a1");

        String sql = "SELECT user_id, password_hash, faculty_name, email FROM faculty WHERE user_id = ?";
        //System.out.println("test a2");

        Map<String, Object> res = querySingleMapped(sql, userid);
        //System.out.println("test a3");

        if(res == null) {
            //System.out.println("test a4");

            return null;
        }
        //System.out.println("test a5");

        return new Professor((String)res.get("user_id"),(String)res.get("password_hash"), (String)res.get("faculty_name"), new Email((String)res.get("email")));
    }

    /**
     * Retrieves information for a faculty member based on their email address.
     *
     * @param email the email address of the faculty member to query
     * @return a Professor object representing the queried faculty member, or null if not found
     */
    public Professor queryFacultyByEmail(String email) {
        String sql = "SELECT user_id, password_hash, faculty_name, email FROM faculty WHERE email = ?";
        Map<String, Object> res = querySingleMapped(sql, email);
        if(res == null) {
            return null;
        }
        return new Professor((String)res.get("user_id"),(String)res.get("password_hash"), (String)res.get("faculty_name"), new Email((String)res.get("email")));
    }

    /**
     * Retrieves a list of faculty members with the given legal name.
     *
     * @param legalName the legal name of the faculty members to query
     * @return a list of Professor objects representing the queried faculty members
     */
    public List<Professor> queryFacultyByLegalName(String legalName) {
        String sql = " SELECT user_id, password_hash, faculty_name, email FROM faculty WHERE faculty_name = ?";
        List<Map<String, Object>> res = queryMultiMapped(sql, legalName);
        List<Professor> facultyList = new ArrayList<>();
        // if(res == null) {
        //     return null;
        // }
        for(Map<String, Object> map: res) {
            Professor faculty = new Professor((String)map.get("user_id"),(String)map.get("password_hash"), (String)map.get("faculty_name"), new Email((String)map.get("email")));
            facultyList.add(faculty);
        }
        return facultyList;
    }

    /**
     * Retrieves information for all faculty members.
     *
     * @return a set of Professor objects representing all faculty members in the database
     */
    public Set<Professor> queryAllFaculty() {
        // String sql = "SELECT user_id, password_hash, legal_name, display_name, email FROM faculty";
        String sql = "SELECT user_id, password_hash, faculty_name, email FROM faculty";
        List<Map<String, Object>> res = queryMultiMapped(sql);
        Set<Professor> facultySet = new HashSet<>();
        // if(res == null) {
        //     return null;
        // }
        for(Map<String, Object> map: res) {
            Professor faculty = new Professor((String)map.get("user_id"),(String)map.get("password_hash"), (String)map.get("faculty_name"), new Email((String)map.get("email")));
            facultySet.add(faculty);
        }
        return facultySet;
    }

}
