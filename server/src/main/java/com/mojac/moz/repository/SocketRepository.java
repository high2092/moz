package com.mojac.moz.repository;

import com.mojac.moz.config.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SocketRepository {

    private List<WebSocketSession> sessions = new ArrayList<>();
    private final SecurityUtil securityUtil;
    private final MemberRepository memberRepository;

    public void save(WebSocketSession session) {
        sessions.add(session);
    }

    public void delete(WebSocketSession session) {
        sessions.remove(session);
    }

    public List<WebSocketSession> findAll() {
        return sessions;
    }

    public List<WebSocketSession> findByRoomId(Long id) {
        return sessions.stream()
                .filter(session -> memberRepository.findById(getPrincipal(session)).get().getRoomId() == id)
                .collect(Collectors.toList());
    }

    private Long getPrincipal(WebSocketSession session) {
        HttpHeaders headers = session.getHandshakeHeaders();
        List<String> cookies = headers.get(HttpHeaders.COOKIE);
        String jwt = getCookie(securityUtil.ACCESS_TOKEN_COOKIE_KEY, cookies.get(0));
        Long memberId = Long.parseLong(securityUtil.extractSubject(jwt));
        return memberId;
    }

    private String getCookie(String key, String cookieString) {
        if (key == null || cookieString == null) {
            return null;
        }

        String[] cookies = cookieString.split(";");
        for (String cookie : cookies) {
            String[] parts = cookie.trim().split("=");
            if (parts.length == 2) {
                String name = parts[0].trim();
                String value = parts[1].trim();
                if (name.equals(key)) {
                    return value;
                }
            }
        }

        return null;
    }
}
