package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.AttendanceRecord;
import edu.duke.ece651.shared.dao.AttendanceRecordDAO;

public class AttendanceRecordService {

    private AttendanceRecordDAO attendanceRecordDAO = new AttendanceRecordDAO();

    public void recordAttendance(AttendanceRecord record) {
        attendanceRecordDAO.addAttendanceRecord(record);
    }

    public void updateAttendanceRecord(AttendanceRecord record) {
        attendanceRecordDAO.updateAttendanceRecord(record);
    }

    public void listAttendanceBySession(int sessionId) {
        attendanceRecordDAO.listAttendanceBySession(sessionId);
    }

    public void listAttendanceByStudentInSection(String studentId, int sectionId) {
        attendanceRecordDAO.listAttendanceByStudentInSection(studentId, sectionId);
    }
}
