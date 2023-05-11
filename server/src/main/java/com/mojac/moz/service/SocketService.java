package com.mojac.moz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojac.moz.domain.Room;
import com.mojac.moz.domain.SocketPayload;
import com.mojac.moz.domain.SocketPayloadType;
import com.mojac.moz.domain.quiz.ConsonantQuiz;
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

    public void sendRoundInfo(Room room) {
        ConsonantQuiz quiz = (ConsonantQuiz) room.getCurrentRoundQuiz();
        SocketPayload payload1 = new SocketPayload(SocketPayloadType.SYSTEM.getValue(), "라운드 " + room.getRound() + " 시작!", "system");
        SocketPayload payload2 = new SocketPayload(SocketPayloadType.ROUND_INFO.getValue(), quiz.getConsonant(), "system");
        sendSocketInRoom(payload1, room.getId());
        sendSocketInRoom(payload2, room.getId());
    }

    public void sendGameStart(Room room) {
        sendSocketInRoom(new SocketPayload(SocketPayloadType.SYSTEM.getValue(), "게임 시작!", "system"), room.getId());
        sendRoundInfo(room);
    }
}
