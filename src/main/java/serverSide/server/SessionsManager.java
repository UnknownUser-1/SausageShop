/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSide.server;

import java.util.HashSet;

/**
 *
 * @author pro56
 */
public class SessionsManager {

    private final HashSet<ClientSession> sessions = new HashSet<>();

    public SessionsManager() {
    }

    public synchronized void addSession(ClientSession session) {
        sessions.add(session);
    }

    public synchronized void removeSession(ClientSession session) {
        sessions.remove(session);
    }
}
