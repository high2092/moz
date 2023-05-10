package com.mojac.moz.service;

import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.Room;
import com.mojac.moz.domain.SocketPayload;
import com.mojac.moz.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final SocketService socketService;
    private final EntityManager entityManager;

    @Transactional
    public void compare(Room room, String answer, Member member) {
        int score = room.compare(answer);

        if (score == 5) {
            member.win(score);

            SocketPayload payload = new SocketPayload("hit", room.getRound() + "R - " + member.getName() + "님이 " + score + "점 획득! (정답: " + answer + ")", "system");
            socketService.sendSocketInRoom(payload, room.getId());

            Room merge = entityManager.merge(room);
            merge.skipRound();
        }
    }

}
