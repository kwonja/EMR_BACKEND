package com.example.demo.patientexam.repository;

import com.example.demo.patientexam.domain.PatientExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

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
}
