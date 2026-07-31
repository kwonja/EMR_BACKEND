package com.example.demo.patient.dto;

import com.example.demo.patient.domain.Patient;

import java.time.Instant;
import java.time.LocalDate;

public class PatientCreateResponse {

    private final Long id;
    private final String patientNumber;
    private final String name;
    private final LocalDate birthDate;
    private final String phone;
    private final Instant createdAt;

    public PatientCreateResponse(
            Long id,
            String patientNumber,
            String name,
            LocalDate birthDate,
            String phone,
            Instant createdAt
    ) {
        this.id = id;
        this.patientNumber = patientNumber;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public static PatientCreateResponse from(Patient patient) {
        return new PatientCreateResponse(
                patient.getId(),
                patient.getPatientNumber(),
                patient.getName(),
                patient.getBirthDate(),
                patient.getPhone(),
                patient.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
