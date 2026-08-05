package com.example.demo.examroomqueue.repository;

import com.example.demo.examroomqueue.domain.ExamRoomQueue;
import com.example.demo.examroomqueue.domain.ExamRoomQueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ExamRoomQueueRepository
        extends JpaRepository<ExamRoomQueue, Long> {

    boolean existsByPatientExam_IdAndStatusIn(
            Long patientExamId,
            Collection<ExamRoomQueueStatus> statuses
    );

    @Query("""
            SELECT CASE WHEN COUNT(queue) > 0 THEN true ELSE false END
            FROM ExamRoomQueue queue
            WHERE queue.patientExam.examCatalog.examinationRoom.id
                    = :examinationRoomId
              AND queue.status = :status
            """)
    boolean existsByExaminationRoomIdAndStatus(
            @Param("examinationRoomId") Long examinationRoomId,
            @Param("status") ExamRoomQueueStatus status
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

    @Query("""
            SELECT queue
            FROM ExamRoomQueue queue
            JOIN FETCH queue.patientExam patientExam
            JOIN FETCH patientExam.patientVisit patientVisit
            JOIN FETCH patientVisit.patient
            JOIN FETCH patientExam.examCatalog examCatalog
            JOIN FETCH examCatalog.examinationRoom room
            LEFT JOIN FETCH queue.assignedStaff
            WHERE (:examinationRoomId IS NULL
                    OR room.id = :examinationRoomId)
              AND (:status IS NULL OR queue.status = :status)
            ORDER BY queue.queueNumber
            """)
    List<ExamRoomQueue> findAllWithDetails(
            @Param("examinationRoomId") Long examinationRoomId,
            @Param("status") ExamRoomQueueStatus status
    );

    @Query("""
            SELECT queue
            FROM ExamRoomQueue queue
            JOIN FETCH queue.patientExam patientExam
            JOIN FETCH patientExam.patientVisit patientVisit
            JOIN FETCH patientVisit.patient
            JOIN FETCH patientExam.examCatalog examCatalog
            JOIN FETCH examCatalog.examinationRoom
            LEFT JOIN FETCH queue.assignedStaff
            WHERE queue.id = :id
            """)
    Optional<ExamRoomQueue> findByIdWithDetails(
            @Param("id") Long id
    );
}
