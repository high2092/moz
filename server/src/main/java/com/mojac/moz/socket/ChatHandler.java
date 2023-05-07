package com.mojac.moz.socket;

import com.mojac.moz.config.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private final SecurityUtil securityUtil;
    private List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        log.info("connect {}", session);
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long memberId = getPrincipal(session);

        String payload = message.getPayload();
        log.info("payload {}", payload);

        for (WebSocketSession s : sessions) {
            s.sendMessage(new TextMessage("[" + memberId + "] " + payload));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("disconnect {}", session);
        sessions.remove(session);
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
