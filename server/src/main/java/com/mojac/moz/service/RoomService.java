package com.mojac.moz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.Room;
import com.mojac.moz.domain.SocketPayload;
import com.mojac.moz.domain.quiz.Quiz;
import com.mojac.moz.repository.QuizRepository;
import com.mojac.moz.repository.RoomRepository;
import com.mojac.moz.repository.SocketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final SocketRepository socketRepository;
    private final RoomRepository roomRepository;
    private final SocketService socketService;
    private final QuizRepository quizRepository;

    @Transactional
    public Long createRoom(String name, int capacity, String password) {
        Room room = new Room(name, capacity, password);
        roomRepository.save(room);
        return room.getId();
    }

    @Transactional(readOnly = true)
    public List<Room> findRooms() {
        return roomRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Room findRoom(Long roomId) {
        return roomRepository.findById(roomId).get();
    }

    @Transactional
    public void enterRoom(Member member, Room room, String password) {
        if (room.size() == room.getCapacity()) throw new RuntimeException();
        if (password != null && !password.equals(room.getPassword())) throw new RuntimeException();

        leaveRoom(member);

        member.enterRoom(room);
        room.enter(member);

        SocketPayload payload = new SocketPayload("system", member.getName() + " enter room" + room.getId(), "system");
        socketService.sendSocketInRoom(payload, room.getId());
    }

    @Transactional
    public void leaveRoom(Member member) {
        Room room = member.getRoom();

        if (room == null) return;

        member.leaveRoom();
        room.leave(member);

        SocketPayload payload = new SocketPayload("system", member.getName() + " leave room" + room.getId(), "system");
        socketService.sendSocketInRoom(payload, room.getId());
//        if (room.size() == 0) roomRepository.delete(room);
    }

    @Transactional
    public void addQuiz(Room room, List<Long> quizIdList) {
        List<Quiz> quizList = quizIdList.stream().map(quizId -> quizRepository.findById(quizId).get()).collect(Collectors.toList()); // TODO: 벌크 쿼리..?
        room.addQuiz(quizList);
        SocketPayload payload = new SocketPayload("addQuiz", "퀴즈가 " + quizList.size() + "개 추가되었습니다.", "system");
        socketService.sendSocketInRoom(payload, room.getId());
    }
}
