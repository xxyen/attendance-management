package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.AttendanceRecord;
import edu.duke.ece651.shared.model.*;
import edu.duke.ece651.shared.dao.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class that manages operations related to attendance records.
 * This class provides methods for recording, updating, listing, and analyzing attendance records associated with sessions and sections.
 */
public class AttendanceRecordService {

    private AttendanceRecordDAO attendanceRecordDAO = new AttendanceRecordDAO();
    private SessionDAO sessionDAO = new SessionDAO();

    /**
     * Records a new attendance entry in the database.
     * @param record The attendance record to be added.
     */
    public void recordAttendance(AttendanceRecord record) {
        attendanceRecordDAO.addAttendanceRecord(record);
    }

     /**
     * Updates an existing attendance record.
     * @param sessionId The session ID associated with the record.
     * @param studentId The student ID associated with the record.
     * @param newStatus The new status to update in the attendance record.
     * @throws Exception if no attendance record is found for the provided session ID and student ID.
     */
    public void updateAttendanceRecord(int sessionId, String studentId, Status newStatus) throws Exception {
        AttendanceRecord record = attendanceRecordDAO.findAttendanceRecordBySessionAndStudent(sessionId, studentId);
        if (record != null) {
            record.setStatus(newStatus);
            attendanceRecordDAO.updateAttendanceRecord(record);
        } else {
            throw new Exception("No attendance record found for session ID: " + sessionId + " and student ID: " + studentId);
        }
    }

     /**
     * Lists all attendance records for a given session.
     * @param sessionId The ID of the session for which attendance is to be listed.
     * @return A list of attendance records for the specified session.
     */
    public List<AttendanceRecord> listAttendanceBySession(int sessionId) {
        return attendanceRecordDAO.listAttendanceBySession(sessionId);
    }

    /**
     * Lists all attendance records for a student within a specific section.
     * @param studentId The ID of the student.
     * @param sectionId The ID of the section.
     * @return A list of attendance records for the specified student and section.
     */
    public List<AttendanceRecord> listAttendanceByStudentInSection(String studentId, int sectionId) {
        return attendanceRecordDAO.listAttendanceByStudentInSection(studentId, sectionId);
    }

     /**
     * Calculates the average attendance score for a student across all sections.
     * @param studentId The ID of the student.
     * @return A map containing section IDs and the corresponding average attendance scores.
     */
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

    /**
     * Calculates the average attendance score for all students in a specific section.
     * @param sectionId The ID of the section.
     * @return A map of student IDs and their corresponding average scores.
     */
    public Map<String, Double> calculateSectionScores(int sectionId) {
        List<AttendanceRecord> records = attendanceRecordDAO.listAttendanceBySection(sectionId);
        Map<String, Double> studentScores = new HashMap<>();
        Map<String, Integer> studentCounts = new HashMap<>();
        // Group records by student and calculate each student's average score
        records.forEach(record -> {
            String studentId = record.getStudentId();
            double newScore = getScore(record.getStatus().getStatus());
            studentScores.compute(studentId, (id, score) -> score == null ? newScore : score + newScore);
            studentCounts.compute(studentId, (id, count) -> count == null ? 1 : count + 1);
        });
        // Divide total scores by counts to get averages
        studentScores.forEach((id, score) -> studentScores.put(id, score / studentCounts.get(id)));
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
