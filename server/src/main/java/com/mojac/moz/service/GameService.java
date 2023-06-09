package com.mojac.moz.service;

import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.Room;
import com.mojac.moz.domain.SocketPayload;
import com.mojac.moz.domain.SocketPayloadType;
import com.mojac.moz.domain.quiz.Quiz;
import com.mojac.moz.exception.internal.AllRoundEndedException;
import com.mojac.moz.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Transactional
    public void compare(Room room, String answer, Member member) {
        int score = room.compare(answer);

        if (score == 5) {
            member.win(score);

            SocketPayload payload = new SocketPayload(SocketPayloadType.SYSTEM.getValue(), room.getRound() + "R - " + member.getName() + "님이 " + score + "점 획득! (정답: " + answer + ")", "system");
            socketService.sendSocketInRoom(payload, room.getId());

            skipRound(room);
        }
    }

    @Transactional
    public void startGame(Room room) {
        room.startGame();
        socketService.sendGameStart(room);
    }

    @Transactional
    public void skipRound(Room room) {
        room = entityManager.merge(room);
        try {
            room.skipRound();
            socketService.sendRoundInfo(room);
        } catch (AllRoundEndedException e) {
            gameOver(room);
        }
    }

    @Transactional
    public void gameOver(Room room) {
        room.gameOver();
        room.getMembers().forEach(member -> {
            member.unready();
            memberRepository.save(member);
        });
        SocketPayload payload = new SocketPayload(SocketPayloadType.GAME_OVER.getValue(), "모든 라운드가 종료되었습니다.", "system");
        socketService.sendSocketInRoom(payload, room.getId());
    }
}
