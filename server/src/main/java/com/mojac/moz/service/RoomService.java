package com.mojac.moz.service;

import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.Room;
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
        return roomRepository.findById(roomId);
    }

    @Transactional
    public void enterRoom(Member member, Room room, String password) {
        if (room.size() == room.getCapacity()) throw new RuntimeException();
        if (password != null && !password.equals(room.getPassword())) throw new RuntimeException();

        leaveRoom(member);

        member.enterRoom(room);
        room.enter(member);

        socketRepository.findByRoomId(room.getId()).forEach(s -> {
            try {
                s.sendMessage(new TextMessage("user " + member.getId() + " enter room " + room.getId()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Transactional
    public void leaveRoom(Member member) {
        Room room = roomRepository.findById(member.getRoomId());

        if (room == null) return;

        member.leaveRoom();
        room.leave(member);


        socketRepository.findByRoomId(room.getId()).forEach(s -> {
            try {
                s.sendMessage(new TextMessage("user " + member.getId() + " leave room " + room.getId()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

//        if (room.size() == 0) roomRepository.delete(room);
    }
}
