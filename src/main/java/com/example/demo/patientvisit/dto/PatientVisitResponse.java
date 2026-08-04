package com.example.demo.patientvisit.dto;

import com.example.demo.patientvisit.domain.PatientVisit;
import com.example.demo.patientvisit.domain.PatientVisitStatus;

import java.time.Instant;

public class PatientVisitResponse {

    private final Long id;
    private final Long patientId;
    private final String patientName;
    private final Long appointmentId;
    private final Instant arrivedAt;
    private final Instant leftAt;
    private final PatientVisitStatus status;

    public PatientVisitResponse(
            Long id,
            Long patientId,
            String patientName,
            Long appointmentId,
            Instant arrivedAt,
            Instant leftAt,
            PatientVisitStatus status
    ) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.appointmentId = appointmentId;
        this.arrivedAt = arrivedAt;
        this.leftAt = leftAt;
        this.status = status;
    }

    public static PatientVisitResponse from(PatientVisit patientVisit) {
        return new PatientVisitResponse(
                patientVisit.getId(),
                patientVisit.getPatient().getId(),
                patientVisit.getPatient().getName(),
                patientVisit.getAppointment() == null
                        ? null
                        : patientVisit.getAppointment().getId(),
                patientVisit.getArrivedAt(),
                patientVisit.getLeftAt(),
                patientVisit.getStatus()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public Instant getArrivedAt() {
        return arrivedAt;
    }

    public Instant getLeftAt() {
        return leftAt;
    }

    public PatientVisitStatus getStatus() {
        return status;
    }
}
