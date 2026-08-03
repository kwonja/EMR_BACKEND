package com.example.demo.examination.repository;

import com.example.demo.examination.domain.ExaminationRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminationRoomRepository extends JpaRepository<ExaminationRoom, Long> {
}
