package com.mojac.moz.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SocketRepository {

    private List<WebSocketSession> sessions = new ArrayList<>();

    public void save(WebSocketSession session) {
        sessions.add(session);
    }

    public void delete(WebSocketSession session) {
        sessions.remove(session);
    }

    public List<WebSocketSession> findAll() {
        return sessions;
    }
}
