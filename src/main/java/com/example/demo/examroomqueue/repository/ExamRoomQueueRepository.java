package com.example.demo.examroomqueue.repository;

import com.example.demo.examroomqueue.domain.ExamRoomQueue;
import com.example.demo.examroomqueue.domain.ExamRoomQueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ExamRoomQueueRepository
        extends JpaRepository<ExamRoomQueue, Long> {

    boolean existsByPatientExam_IdAndStatusIn(
            Long patientExamId,
            Collection<ExamRoomQueueStatus> statuses
    );

    @Query("""
            SELECT COALESCE(MAX(queue.queueNumber), 0)
            FROM ExamRoomQueue queue
            WHERE queue.patientExam.examCatalog.examinationRoom.id
                    = :examinationRoomId
            """)
    int findMaxQueueNumberByExaminationRoomId(
            @Param("examinationRoomId") Long examinationRoomId
    );
}
