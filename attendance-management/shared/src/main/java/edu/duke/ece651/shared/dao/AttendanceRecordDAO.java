package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.model.AttendanceRecord;
import edu.duke.ece651.shared.model.Status;

import java.util.List;

public class AttendanceRecordDAO extends BasicDAO<AttendanceRecord> {

    public int addAttendanceRecord(AttendanceRecord record) {
        String sql = "INSERT INTO attendance_record (session_id, student_id, status) VALUES (?, ?, ?)";
        return update(sql, record.getSessionId(), record.getStudentId(), record.getStatus().getStatus());
    }

    public int deleteAttendanceRecord(Integer recordId) {
        String sql = "DELETE FROM attendance_record WHERE record_id = ?";
        return update(sql, recordId);
    }

    public int updateAttendanceRecord(AttendanceRecord record) {
        String sql = "UPDATE attendance_record SET session_id = ?, student_id = ?, status = ? WHERE record_id = ?";
        return update(sql, record.getSessionId(), record.getStudentId(), record.getStatus().getStatus(), record.getRecordId());
    }

    public List<AttendanceRecord> listAttendanceBySession(int sessionId){
        String sql = "SELECT record_id AS recordId, session_id AS sessionId, student_id AS studentId, status AS status FROM attendance_record WHERE session_id = ?";
        return queryMulti(sql, AttendanceRecord.class, sessionId);
    }

    public List<AttendanceRecord> listAttendanceByStudentInSection(String studentId, int sectionId){
        String sql = "SELECT record_id AS recordId, session_id AS sessionId, student_id AS studentId, status AS status FROM attendance_record WHERE student_id = ? AND section_id = ?";
        return queryMulti(sql, AttendanceRecord.class, studentId, sectionId);
    }

    public AttendanceRecord findAttendanceRecordBySessionAndStudent(int sessionId, String studentId) {
        String sql = "SELECT * FROM attendance_record WHERE session_id = ? AND student_id = ?";
        return querySingle(sql, AttendanceRecord.class, sessionId, studentId);
    }    

    public List<AttendanceRecord> listAttendanceByStudent(String studentId) {
        String sql = "SELECT * FROM attendance_record WHERE student_id = ?";
        return queryMulti(sql, AttendanceRecord.class, studentId);
    }
    
    public List<AttendanceRecord> listAttendanceBySection(int sectionId) {
        String sql = "SELECT * FROM attendance_record WHERE session_id IN (SELECT session_id FROM session WHERE section_id = ?)";
        return queryMulti(sql, AttendanceRecord.class, sectionId);
    }
}
