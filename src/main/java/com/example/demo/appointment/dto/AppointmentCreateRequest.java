package com.example.demo.appointment.dto;

import java.time.LocalDateTime;

public class AppointmentCreateRequest {

    private Long patientId;
    private LocalDateTime scheduledAt;

    public AppointmentCreateRequest() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

}
