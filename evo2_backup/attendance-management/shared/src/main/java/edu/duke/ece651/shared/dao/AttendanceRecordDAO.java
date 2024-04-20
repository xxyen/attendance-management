package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.model.AttendanceRecord;
import edu.duke.ece651.shared.model.Status;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The AttendanceRecordDAO class provides data access operations for attendance records.
 * It extends the BasicDAO class and inherits basic data access methods.
 */
public class AttendanceRecordDAO extends BasicDAO<AttendanceRecord> {

    /**
     * Adds a new attendance record to the database.
     *
     * @param record the AttendanceRecord object representing the record to be added
     * @return the record ID generated for the new record
     */
    public int addAttendanceRecord(AttendanceRecord record) {
        String sql = "INSERT INTO attendance_record (session_id, student_id, status) VALUES (?, ?, ?)";
        // return update(sql, record.getSessionId(), record.getStudentId(), record.getStatus().getStatus());
        String status = String.valueOf(record.getStatus().getStatus());
        long generatedId = insertAndGetGeneratedKey(sql, record.getSessionId(), record.getStudentId(), status);
        record.setRecordId((int) generatedId); 
        return (int)generatedId;
    }

    /**
     * Deletes an attendance record from the database based on its record ID.
     *
     * @param recordId the ID of the record to be deleted
     * @return the number of rows affected by the delete operation
     */
    public int deleteAttendanceRecord(Integer recordId) {
        String sql = "DELETE FROM attendance_record WHERE record_id = ?";
        return update(sql, recordId);
    }

    /**
     * Updates information for an existing attendance record in the database.
     *
     * @param record the AttendanceRecord object representing the updated information
     * @return the number of rows affected by the update operation
     */
    public int updateAttendanceRecord(AttendanceRecord record) {
        String sql = "UPDATE attendance_record SET session_id = ?, student_id = ?, status = ? WHERE record_id = ?";
        return update(sql, record.getSessionId(), record.getStudentId(), String.valueOf(record.getStatus().getStatus()), record.getRecordId());
    }

    /**
     * Retrieves a list of attendance records for a given session.
     *
     * @param sessionId the ID of the session
     * @return a list of AttendanceRecord objects representing the attendance records for the session
     */
    public List<AttendanceRecord> listAttendanceBySession(int sessionId){
        String sql = "SELECT record_id, session_id, student_id, status FROM attendance_record WHERE session_id = ?";
        // return queryMulti(sql, AttendanceRecord.class, sessionId);

        List<Map<String, Object>> res = queryMultiMapped(sql, sessionId);
        List<AttendanceRecord> recordList = new ArrayList<>();
        // if(res == null) {
        //     return null;
        // }
        for(Map<String, Object> map: res) {
            AttendanceRecord record =  new AttendanceRecord((int)map.get("record_id"), (int)map.get("session_id"), (String)map.get("student_id"), new Status(((String)map.get("status")).charAt(0)));
            recordList.add(record);
        }
        return recordList;
    }

    /**
     * Retrieves a list of attendance records for a given student in a section.
     *
     * @param studentId the ID of the student
     * @param sectionId the ID of the section
     * @return a list of AttendanceRecord objects representing the attendance records for the student in the section
     */
    public List<AttendanceRecord> listAttendanceByStudentInSection(String studentId, int sectionId){
        String sql = "SELECT ar.record_id, ar.session_id, ar.student_id, ar.status " +
                    "FROM attendance_record ar " +
                    "JOIN session s ON ar.session_id = s.session_id " +
                    "JOIN section sec ON s.section_id = sec.section_id " +
                    "WHERE ar.student_id = ? AND sec.section_id = ?";
        // return queryMulti(sql, AttendanceRecord.class, studentId, sectionId);

        List<Map<String, Object>> res = queryMultiMapped(sql, studentId, sectionId);
        List<AttendanceRecord> recordList = new ArrayList<>();
        // if(res == null) {
        //     return null;
        // }
        for(Map<String, Object> map: res) {
            AttendanceRecord record =  new AttendanceRecord((int)map.get("record_id"), (int)map.get("session_id"), (String)map.get("student_id"), new Status(((String)map.get("status")).charAt(0)));
            recordList.add(record);
        }
        return recordList;
    }

    /**
     * Retrieves an attendance record for a given session and student.
     *
     * @param sessionId the ID of the session
     * @param studentId the ID of the student
     * @return the AttendanceRecord object representing the attendance record for the session and student, or null if not found
     */
    public AttendanceRecord findAttendanceRecordBySessionAndStudent(int sessionId, String studentId) {
        String sql = "SELECT record_id, session_id, student_id, status FROM attendance_record WHERE session_id = ? AND student_id = ?";
        // return querySingle(sql, AttendanceRecord.class, sessionId, studentId);

        Map<String, Object> res = querySingleMapped(sql, sessionId, studentId);
        // if(res == null) {
        //     return null;
        // }
        return new AttendanceRecord((int)res.get("record_id"), (int)res.get("session_id"), (String)res.get("student_id"), new Status(((String)res.get("status")).charAt(0)));
    }    

    /**
     * Retrieves a list of attendance records for a given student.
     *
     * @param studentId the ID of the student
     * @return a list of AttendanceRecord objects representing the attendance records for the student
     */
    public List<AttendanceRecord> listAttendanceByStudent(String studentId) {
        String sql = "SELECT record_id, session_id, student_id, status AS status FROM attendance_record WHERE student_id = ?";
        // return queryMulti(sql, AttendanceRecord.class, studentId);

        List<Map<String, Object>> res = queryMultiMapped(sql, studentId);
        List<AttendanceRecord> recordList = new ArrayList<>();
        // if(res == null) {
        //     return null;
        // }
        for(Map<String, Object> map: res) {
            AttendanceRecord record =  new AttendanceRecord((int)map.get("record_id"), (int)map.get("session_id"), (String)map.get("student_id"), new Status(((String)map.get("status")).charAt(0)));
            recordList.add(record);
        }
        return recordList;
    }
    
    /**
 * Retrieves a list of attendance records for a given section.
 *
 * @param sectionId the ID of the section
 * @return a list of AttendanceRecord objects representing the attendance records for the section
 */
    public List<AttendanceRecord> listAttendanceBySection(int sectionId) {
        String sql =  "SELECT ar.record_id, ar.session_id, ar.student_id, ar.status " +
        "FROM attendance_record ar " +
        "JOIN session s ON ar.session_id = s.session_id " +
        "WHERE s.section_id = ?";
        // return queryMulti(sql, AttendanceRecord.class, sectionId);

        List<Map<String, Object>> res = queryMultiMapped(sql, sectionId);
        List<AttendanceRecord> recordList = new ArrayList<>();
        // if(res == null) {
        //     return null;
        // }
        for(Map<String, Object> map: res) {
            AttendanceRecord record =  new AttendanceRecord((int)map.get("record_id"), (int)map.get("session_id"), (String)map.get("student_id"), new Status(((String)map.get("status")).charAt(0)));
            recordList.add(record);
        }
        return recordList;
    }
}
