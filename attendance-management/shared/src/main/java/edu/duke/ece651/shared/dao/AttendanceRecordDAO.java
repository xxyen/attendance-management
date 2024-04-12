package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.model.AttendanceRecord;
import edu.duke.ece651.shared.model.Status;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AttendanceRecordDAO extends BasicDAO<AttendanceRecord> {

    public int addAttendanceRecord(AttendanceRecord record) {
        String sql = "INSERT INTO attendance_record (session_id, student_id, status) VALUES (?, ?, ?)";
        // return update(sql, record.getSessionId(), record.getStudentId(), record.getStatus().getStatus());
        String status = String.valueOf(record.getStatus().getStatus());
        long generatedId = insertAndGetGeneratedKey(sql, record.getSessionId(), record.getStudentId(), status);
        record.setRecordId((int) generatedId); 
        return (int)generatedId;
    }

    public int deleteAttendanceRecord(Integer recordId) {
        String sql = "DELETE FROM attendance_record WHERE record_id = ?";
        return update(sql, recordId);
    }

    public int updateAttendanceRecord(AttendanceRecord record) {
        String sql = "UPDATE attendance_record SET session_id = ?, student_id = ?, status = ? WHERE record_id = ?";
        return update(sql, record.getSessionId(), record.getStudentId(), String.valueOf(record.getStatus().getStatus()), record.getRecordId());
    }

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

    public AttendanceRecord findAttendanceRecordBySessionAndStudent(int sessionId, String studentId) {
        String sql = "SELECT record_id, session_id, student_id, status FROM attendance_record WHERE session_id = ? AND student_id = ?";
        // return querySingle(sql, AttendanceRecord.class, sessionId, studentId);

        Map<String, Object> res = querySingleMapped(sql, sessionId, studentId);
        // if(res == null) {
        //     return null;
        // }
        return new AttendanceRecord((int)res.get("record_id"), (int)res.get("session_id"), (String)res.get("student_id"), new Status(((String)res.get("status")).charAt(0)));
    }    

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
