package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.Session;
import edu.duke.ece651.shared.dao.SessionDAO;

public class SessionService {

    private SessionDAO sessionDAO = new SessionDAO();

    public void addSessionToSection(int sectionId, Session session) {
        session.setSectionId(sectionId);
        sessionDAO.addSession(session);
    }

    public void updateSession(Session session) {
        sessionDAO.updateSession(session);
    }

    public void deleteSession(int sessionId) {
        sessionDAO.deleteSession(sessionId);
    }

    public void listSessionsBySection(int sectionId) {
        sessionDAO.listSessionsBySection(sectionId);
    }
}
