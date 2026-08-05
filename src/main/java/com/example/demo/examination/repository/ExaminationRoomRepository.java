package com.example.demo.examination.repository;

import com.example.demo.examination.domain.ExaminationRoom;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExaminationRoomRepository extends JpaRepository<ExaminationRoom, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT room
            FROM ExaminationRoom room
            WHERE room.id = :id
            """)
    Optional<ExaminationRoom> findByIdForUpdate(@Param("id") Long id);

    @Query("""
            SELECT room
            FROM ExaminationRoom room
            WHERE (:location IS NULL OR room.location = :location)
              AND (:roomName IS NULL OR room.name = :roomName)
              AND (:roomNo IS NULL OR room.roomNo = :roomNo)
            ORDER BY room.location, room.name, room.roomNo
            """)
    List<ExaminationRoom> findAllByFilters(
            @Param("location") String location,
            @Param("roomName") String roomName,
            @Param("roomNo") String roomNo
    );
}
