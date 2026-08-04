package com.example.demo.patientexam.repository;

import com.example.demo.patientexam.domain.PatientExam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientExamRepository extends JpaRepository<PatientExam, Long> {

    boolean existsByPatientVisit_IdAndSequenceNumber(
            Long patientVisitId,
            int sequenceNumber
    );
}
