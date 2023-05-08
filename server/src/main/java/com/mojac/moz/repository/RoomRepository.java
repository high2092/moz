package com.mojac.moz.repository;

import com.mojac.moz.domain.Room;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RoomRepository {

    private final Map<Long, Room> store = new HashMap<>();
    private Long sequence = 0L;

    public Long save(Room room) {
        room.register(++sequence);
        store.put(room.getId(), room);
        return sequence;
    }

    public List<Room> findAll() {
        return store.values().stream().collect(Collectors.toList());
    }

    public Room findById(Long id) {
        return store.get(id);
    }

    public void delete(Room room) {
        store.remove(room.getId());
    }
}
