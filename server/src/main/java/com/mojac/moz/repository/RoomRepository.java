package com.mojac.moz.repository;

import com.mojac.moz.domain.Room;
import com.mojac.moz.domain.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Modifying
    @Query("update Room r set r.status = :status")
    int init(RoomStatus status);
}
