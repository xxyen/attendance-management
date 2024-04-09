package edu.duke.ece651.shared.service;

import edu.duke.ece651.shared.model.Session;
import edu.duke.ece651.shared.dao.SessionDAO;
import java.util.List;


public class SessionService {

    private SessionDAO sessionDAO = new SessionDAO();

    public void addSessionToSection(int sectionId, Session session) {
        sessionDAO.addSession(session);
    }

    public void updateSession(Session session) {
        sessionDAO.updateSession(session);
    }

    public void deleteSession(int sessionId) {
        sessionDAO.deleteSession(sessionId);
    }

    public List<Session> listSessionsBySection(int sectionId) {
        return sessionDAO.listSessionsBySection(sectionId);
    }
}
