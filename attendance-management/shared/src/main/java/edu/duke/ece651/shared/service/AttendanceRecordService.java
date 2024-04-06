package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.AttendanceRecord;
import edu.duke.ece651.shared.model.Status;
import edu.duke.ece651.shared.dao.AttendanceRecordDAO;

public class AttendanceRecordService {

    private AttendanceRecordDAO attendanceRecordDAO = new AttendanceRecordDAO();

    public void recordAttendance(AttendanceRecord record) {
        attendanceRecordDAO.addAttendanceRecord(record);
    }

    public void updateAttendanceRecord(int sessionId, String studentId, Status newStatus) throws Exception {
        AttendanceRecord record = attendanceRecordDAO.findAttendanceRecordBySessionAndStudent(sessionId, studentId);
        if (record != null) {
            record.setStatus(newStatus);
            attendanceRecordDAO.updateAttendanceRecord(record);
        } else {
            throw new Exception("No attendance record found for session ID: " + sessionId + " and student ID: " + studentId);
        }
    }

    public void listAttendanceBySession(int sessionId) {
        attendanceRecordDAO.listAttendanceBySession(sessionId);
    }

    public void listAttendanceByStudentInSection(String studentId, int sectionId) {
        attendanceRecordDAO.listAttendanceByStudentInSection(studentId, sectionId);
    }
}
