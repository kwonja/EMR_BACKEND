package com.example.demo.appointment.dto;

import com.example.demo.appointment.domain.Appointment;
import com.example.demo.appointment.domain.AppointmentStatus;

import java.time.Instant;
import java.time.LocalDateTime;

public class AppointmentResponse {

    private final Long id;
    private final Long patientId;
    private final String patientName;
    private final Long departmentId;
    private final String departmentName;
    private final Long staffId;
    private final String staffName;
    private final LocalDateTime scheduledAt;
    private final AppointmentStatus status;
    private final String symptoms;
    private final Instant createdAt;

    public AppointmentResponse(
            Long id,
            Long patientId,
            String patientName,
            Long departmentId,
            String departmentName,
            Long staffId,
            String staffName,
            LocalDateTime scheduledAt,
            AppointmentStatus status,
            String symptoms,
            Instant createdAt
    ) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.staffId = staffId;
        this.staffName = staffName;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.symptoms = symptoms;
        this.createdAt = createdAt;
    }

    public static AppointmentResponse from(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatient().getId(),
                appointment.getPatient().getName(),
                appointment.getDepartment().getId(),
                appointment.getDepartment().getName(),
                appointment.getStaff().getId(),
                appointment.getStaff().getName(),
                appointment.getScheduledAt(),
                appointment.getStatus(),
                appointment.getSymptoms(),
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Long getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
