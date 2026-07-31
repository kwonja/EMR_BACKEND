package com.example.demo.patient.repository;

import com.example.demo.patient.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
