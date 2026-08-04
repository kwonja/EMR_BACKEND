package com.example.demo.appointment.dto;

import com.example.demo.appointment.domain.Appointment;
import com.example.demo.appointment.domain.AppointmentStatus;

import java.time.Instant;
import java.time.LocalDateTime;

public class AppointmentResponse {

    private final Long id;
    private final Long patientId;
    private final String patientName;
    private final LocalDateTime scheduledAt;
    private final AppointmentStatus status;
    private final Instant createdAt;

    public AppointmentResponse(
            Long id,
            Long patientId,
            String patientName,
            LocalDateTime scheduledAt,
            AppointmentStatus status,
            Instant createdAt
    ) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static AppointmentResponse from(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatient().getId(),
                appointment.getPatient().getName(),
                appointment.getScheduledAt(),
                appointment.getStatus(),
                appointment.getCreatedAt()
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

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
