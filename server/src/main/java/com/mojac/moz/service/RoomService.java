package com.mojac.moz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.Room;
import com.mojac.moz.domain.SocketPayload;
import com.mojac.moz.repository.RoomRepository;
import com.mojac.moz.repository.SocketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final SocketRepository socketRepository;
    private final RoomRepository roomRepository;
    private final SocketService socketService;
    private final ObjectMapper objectMapper;

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
    public void startGame(Room room) {
        room.startGame();
        socketService.sendSocketInRoom(new SocketPayload("gameStart", "게임 시작!", "system"), room.getId());
    }
}
