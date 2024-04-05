package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.AttendanceRecord;
import edu.duke.ece651.shared.Status;

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

}
