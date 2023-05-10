package com.mojac.moz.service;

import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.Room;
import com.mojac.moz.domain.SocketPayload;
import com.mojac.moz.repository.MemberRepository;
import com.mojac.moz.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final SocketService socketService;
    private final RoomService roomService;

    @Transactional
    public void ready(Long memberId) {
        Member member = memberRepository.findById(memberId).get();

        if (member.getRoomId() == null) {
            throw new RuntimeException();
        }
        if (member.getIsReady() == true) {
            throw new RuntimeException();
        }

        member.ready();

        socketService.sendSocketInRoom(new SocketPayload("ready", member.getName() + " 준비", "system"), member.getRoomId());

        Room room = roomRepository.findById(member.getRoomId());

        if (room.checkAllReady()) {
            roomService.startGame(room);
        }
    }

    @Transactional
    public void unready(Long memberId) {
        Member member = memberRepository.findById(memberId).get();

        if (member.getRoomId() == null) {
            throw new RuntimeException();
        }
        if (member.getIsReady() == false) {
            throw new RuntimeException();
        }

        member.unready();
    }
}
