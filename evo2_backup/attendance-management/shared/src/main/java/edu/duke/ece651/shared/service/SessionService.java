package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.Session;
import edu.duke.ece651.shared.dao.SessionDAO;
import java.util.List;

/**
 * Service class that manages session operations.
 * This class provides a high-level interface for interacting with session-related data through the SessionDAO.
 */
public class SessionService {

    private SessionDAO sessionDAO = new SessionDAO();

    /**
     * Adds a session to a specified section.
     * This method creates a new session associated with a given section identifier.
     * @param sectionId the identifier of the section to which the session will be added.
     * @param session the session object containing the session details to be added.
     */
    public void addSessionToSection(int sectionId, Session session) {
        sessionDAO.addSession(session);
    }

     /**
     * Updates an existing session in the database.
     * This method modifies a session's details in the database using the provided session object.
     * @param session the session object containing updated details to be applied.
     */
    public void updateSession(Session session) {
        sessionDAO.updateSession(session);
    }

    /**
     * Deletes a session from the database.
     * This method removes a session identified by its session ID.
     * @param sessionId the identifier of the session to be deleted.
     */
    public void deleteSession(int sessionId) {
        sessionDAO.deleteSession(sessionId);
    }

    /**
     * Retrieves a list of sessions associated with a specific section.
     * This method returns all sessions linked to a particular section, identified by section ID.
     * @param sectionId the identifier of the section whose sessions are to be listed.
     * @return a list of session objects associated with the specified section.
     */
    public List<Session> listSessionsBySection(int sectionId) {
        return sessionDAO.listSessionsBySection(sectionId);
    }
}
