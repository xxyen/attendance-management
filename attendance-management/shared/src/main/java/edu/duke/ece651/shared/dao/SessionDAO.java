package edu.duke.ece651.shared.dao;

import edu.duke.ece651.shared.model.Session;
import java.util.List;

/**
 * The SessionDAO class provides data access operations specific to sessions.
 * It extends the BasicDAO class and inherits basic data access methods.
 */
public class SessionDAO extends BasicDAO<Session> {

    /**
     * Retrieves information for all sessions in the database.
     *
     * @return a list of Session objects representing all sessions
     */
    public List<Session> findAllSessions() {
        String sql = "SELECT session_id AS sessionId, section_id AS sectionId, session_date AS sessionDate, start_time AS startTime, end_time AS endTime FROM session";
        return queryMulti(sql, Session.class);
    }

    /**
     * Retrieves information for a session based on its ID.
     *
     * @param sessionId the ID of the session to query
     * @return a Session object representing the queried session, or null if not found
     */
    public Session findSessionById(int sessionId) {
        String sql = "SELECT session_id AS sessionId, section_id AS sectionId, session_date AS sessionDate, start_time AS startTime, end_time AS endTime FROM session WHERE session_id = ?";
        return querySingle(sql, Session.class, sessionId);
    }

    /**
     * Adds a new session to the database.
     *
     * @param session the Session object representing the session to be added
     * @return the ID of the newly added session
     */
    public int addSession(Session session) {
        String sql = "INSERT INTO session (section_id, session_date, start_time, end_time) VALUES (?, ?, ?, ?)";
        // return update(sql, session.getSectionId(), session.getSessionDate(), session.getStartTime(), session.getEndTime());
        long generatedId = insertAndGetGeneratedKey(sql, session.getSectionId(), session.getSessionDate(), session.getStartTime(), session.getEndTime());
        session.setSessionId((int) generatedId);
        return (int)generatedId;
    }

    /**
     * Deletes a session from the database based on its ID.
     *
     * @param sessionId the ID of the session to be deleted
     * @return the number of rows affected by the delete operation
     */
    public int deleteSession(int sessionId) {
        String sql = "DELETE FROM session WHERE session_id = ?";
        return update(sql, sessionId);
    }

    /**
     * Updates information for an existing session in the database.
     *
     * @param session the Session object representing the updated information
     * @return the number of rows affected by the update operation
     */
    public int updateSession(Session session) {
        String sql = "UPDATE session SET section_id = ?, session_date = ?, start_time = ?, end_time = ? WHERE session_id = ?";
        return update(sql, session.getSectionId(), session.getSessionDate(), session.getStartTime(), session.getEndTime(), session.getSessionId());
    }

    /**
     * Retrieves a list of sessions for a given section.
     *
     * @param sectionId the ID of the section to query
     * @return a list of Session objects representing the sessions for the section
     */
    public List<Session> listSessionsBySection(int sectionId){
        String sql = "SELECT session_id AS sessionId, section_id AS sectionId, session_date AS sessionDate, start_time AS startTime, end_time AS endTime FROM session WHERE section_id = ?";
        return queryMulti(sql, Session.class, sectionId);
    }

}
