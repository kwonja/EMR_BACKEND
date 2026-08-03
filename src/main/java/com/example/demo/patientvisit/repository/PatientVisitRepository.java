package com.example.demo.patientvisit.repository;

import com.example.demo.patientvisit.domain.PatientVisit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientVisitRepository extends JpaRepository<PatientVisit, Long> {
}
