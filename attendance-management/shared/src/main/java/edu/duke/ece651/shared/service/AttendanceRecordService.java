package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.AttendanceRecord;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.dao.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class AttendanceRecordService {

    private AttendanceRecordDAO attendanceRecordDAO = new AttendanceRecordDAO();
    private SessionDAO sessionDAO = new SessionDAO();

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

    public List<AttendanceRecord> listAttendanceBySession(int sessionId) {
        return attendanceRecordDAO.listAttendanceBySession(sessionId);
    }

    public List<AttendanceRecord> listAttendanceByStudentInSection(String studentId, int sectionId) {
        return attendanceRecordDAO.listAttendanceByStudentInSection(studentId, sectionId);
    }

    // Calculate the average attendance score for a specific student in each section
    public Map<Integer, Double> calculateStudentScoreBySection(String studentId) {
        List<AttendanceRecord> records = attendanceRecordDAO.listAttendanceByStudent(studentId);
        Map<Integer, List<Session>> sessionsBySection = sessionDAO.findAllSessions().stream().collect(Collectors.groupingBy(Session::getSectionId));
        
        Map<Integer, Double> scoresBySection = new HashMap<>();
        Map<Integer, Integer> countsBySection = new HashMap<>();

        // Iterate over all records to calculate scores per section
        for (AttendanceRecord record : records) {
            Session session = sessionDAO.findSessionById(record.getSessionId());
            if (session != null && sessionsBySection.containsKey(session.getSectionId())) {
                int sectionId = session.getSectionId();
                double score = getScore(record.getStatus().getStatus());
                
                scoresBySection.merge(sectionId, score, Double::sum);
                countsBySection.merge(sectionId, 1, Integer::sum);
            }
        }
        
        // Calculate average score for each section
        Map<Integer, Double> averageScoresBySection = new HashMap<>();
        scoresBySection.forEach((sectionId, totalScore) -> {
            double avgScore = totalScore / countsBySection.get(sectionId);
            averageScoresBySection.put(sectionId, avgScore);
        });

        return averageScoresBySection;
    }

    // Calculate the average attendance score for all students in a specific section
    public Map<String, Double> calculateSectionScores(int sectionId) {
        List<AttendanceRecord> records = attendanceRecordDAO.listAttendanceBySection(sectionId);
        Map<String, Double> studentScores = new HashMap<>();
        // Group records by student and calculate each student's average score
        records.forEach(record -> {
            studentScores.compute(record.getStudentId(), (id, score) -> score == null ? getScore(record.getStatus().getStatus()) : (score + getScore(record.getStatus().getStatus())) / 2);
        });
        return studentScores;
    }

    // Calculate the average attendance score for a specific student in a specific section
    public double calculateStudentSectionScore(String studentId, int sectionId) {
        List<AttendanceRecord> records = attendanceRecordDAO.listAttendanceByStudentInSection(studentId, sectionId);
        return calculateAverageScore(records);
    }

    // Calculate average score from a list of attendance records
    private double calculateAverageScore(List<AttendanceRecord> records) {
        if (records.isEmpty()) return 0.0;
        double totalScore = 0.0;
        for (AttendanceRecord record : records) {
            totalScore += getScore(record.getStatus().getStatus());
        }
        return totalScore / records.size();
    }

    // Convert attendance status to score
    private double getScore(char status) {
        switch (status) {
            case 'p':
                return 100.0;
            case 't':
                return 80.0;
            case 'a':
                return 0.0;
            default:
                throw new IllegalArgumentException("Invalid attendance status: " + status);
        }
    }
}
