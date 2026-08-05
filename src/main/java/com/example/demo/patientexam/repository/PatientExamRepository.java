package com.example.demo.patientexam.repository;

import com.example.demo.patientexam.domain.PatientExam;
import com.example.demo.patientexam.domain.PatientExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface PatientExamRepository extends JpaRepository<PatientExam, Long> {

    boolean existsByPatientVisit_IdAndSequenceNumber(
            Long patientVisitId,
            int sequenceNumber
    );

    @Query("""
            SELECT patientExam
            FROM PatientExam patientExam
            JOIN FETCH patientExam.patientVisit patientVisit
            JOIN FETCH patientVisit.patient
            JOIN FETCH patientExam.examCatalog examCatalog
            JOIN FETCH examCatalog.examinationRoom
            WHERE patientExam.id = :id
            """)
    Optional<PatientExam> findByIdWithDetails(@Param("id") Long id);

    @Query("""
            SELECT patientExam
            FROM PatientExam patientExam
            JOIN FETCH patientExam.examCatalog examCatalog
            JOIN FETCH examCatalog.examinationRoom
            WHERE patientExam.patientVisit.id = :patientVisitId
              AND (:status IS NULL OR patientExam.status = :status)
            ORDER BY patientExam.sequenceNumber
            """)
    List<PatientExam> findAllWithDetailsByPatientVisitId(
            @Param("patientVisitId") Long patientVisitId,
            @Param("status") PatientExamStatus status
    );

    @Query("""
            SELECT MIN(patientExam.sequenceNumber)
            FROM PatientExam patientExam
            WHERE patientExam.patientVisit.id = :patientVisitId
              AND patientExam.status = :status
            """)
    Integer findFirstSequenceNumberByStatus(
            @Param("patientVisitId") Long patientVisitId,
            @Param("status") PatientExamStatus status
    );

    long countByPatientVisit_Id(Long patientVisitId);

    boolean existsByPatientVisit_IdAndStatusNot(
            Long patientVisitId,
            PatientExamStatus status
    );
}
