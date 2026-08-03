package com.example.demo.appointment.domain;

import com.example.demo.department.domain.Department;
import com.example.demo.patient.domain.Patient;
import com.example.demo.staff.domain.Staff;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AppointmentStatus status = AppointmentStatus.BOOKED;

    @Column(length = 500)
    private String symptoms;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Appointment() {
    }

    public Appointment(
            Patient patient,
            Department department,
            Staff staff,
            LocalDateTime scheduledAt,
            String symptoms
    ) {
        this.patient = Objects.requireNonNull(patient);
        this.department = Objects.requireNonNull(department);
        this.staff = Objects.requireNonNull(staff);
        this.scheduledAt = Objects.requireNonNull(scheduledAt);
        this.symptoms = symptoms;
    }

    @PrePersist
    private void initializeCreatedAt() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public void updateStatus(AppointmentStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Department getDepartment() {
        return department;
    }

    public Staff getStaff() {
        return staff;
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
