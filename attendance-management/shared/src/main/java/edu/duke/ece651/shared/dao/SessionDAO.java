package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.model.Session;
import java.util.List;

public class SessionDAO extends BasicDAO<Session> {

    public List<Session> findAllSessions() {
        String sql = "SELECT * FROM session";
        return queryMulti(sql, Session.class);
    }

    public Session findSessionById(int sessionId) {
        String sql = "SELECT session_id AS sessionId, section_id AS sectionId, session_date AS sessionDate, start_time AS startTime, end_time AS endTime FROM session WHERE session_id = ?";
        return querySingle(sql, Session.class, sessionId);
    }

    public int addSession(Session session) {
        String sql = "INSERT INTO session (section_id, session_date, start_time, end_time) VALUES (?, ?, ?, ?)";
        return update(sql, session.getSectionId(), session.getSessionDate(), session.getStartTime(), session.getEndTime());
    }

    public int deleteSession(int sessionId) {
        String sql = "DELETE FROM session WHERE session_id = ?";
        return update(sql, sessionId);
    }

    public int updateSession(Session session) {
        String sql = "UPDATE session SET section_id = ?, session_date = ?, start_time = ?, end_time = ? WHERE session_id = ?";
        return update(sql, session.getSectionId(), session.getSessionDate(), session.getStartTime(), session.getEndTime(), session.getSessionId());
    }

    public List<Session> listSessionsBySection(int sectionId){
        String sql = "SELECT * FROM session WHERE section_id = ?";
        return queryMulti(sql, Session.class, sectionId);
    }

}
