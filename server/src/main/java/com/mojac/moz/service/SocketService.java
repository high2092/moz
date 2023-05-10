package com.mojac.moz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojac.moz.domain.SocketPayload;
import com.mojac.moz.repository.SocketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SocketService {

    private final ObjectMapper objectMapper;
    private final SocketRepository socketRepository;

    public void sendSocketInRoom(SocketPayload payload, Long roomId) {
        socketRepository.findByRoomId(roomId).forEach((session -> {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(payload)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
