package com.mojac.moz.controller;

import com.mojac.moz.config.SecurityUtil;
import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.Room;
import com.mojac.moz.repository.MemberRepository;
import com.mojac.moz.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final SecurityUtil securityUtil;
    private final MemberRepository memberRepository;
    private final RoomService roomService;

    @GetMapping
    public GetRoomsResponse getRooms() {
        return new GetRoomsResponse(roomService.findRooms().stream()
                .map(room -> new RoomDto(room))
                .collect(Collectors.toList())
        );
    }

    @PostMapping
    public Long createRoom(@RequestBody CreateRoomRequest request) {
        Long roomId = roomService.createRoom(request.getName(), request.getCapacity(), request.getPassword());
        return roomId;
    }

    @GetMapping("/{roomId}")
    public List<Long> enterRoom(@PathVariable Long roomId, Authentication authentication) {
        Long memberId = securityUtil.getPrincipal(authentication);
        Member member = memberRepository.findById(memberId).get();
        Room room = roomService.findRoom(roomId);
        roomService.enterRoom(member, room, null);

        return room.getMembers().stream().map(m -> m.getId()).collect(Collectors.toList());
    }

    @Getter
    static class RoomDto {
        private Long id;
        private String name;
        private int capacity;
        private int size;
        private boolean requirePassword;

        public RoomDto(Room room) {
            this.id = room.getId();
            this.name = room.getName();
            this.capacity = room.getCapacity();
            this.size = room.size();
            this.requirePassword = room.requirePassword();
        }
    }

    @Getter
    @AllArgsConstructor
    static class GetRoomsResponse {
        List<RoomDto> rooms;
    }

    @Getter
    static class CreateRoomRequest {
        private String name;
        private int capacity;
        private String password;
    }

    @Getter
    static class EnterRoomRequest {
        String password;
    }
}
