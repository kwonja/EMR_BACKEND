package com.example.demo.patientvisit.domain;

import com.example.demo.appointment.domain.Appointment;
import com.example.demo.patient.domain.Patient;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "patient_visits")
public class PatientVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id", unique = true)
    private Appointment appointment;

    @Column(name = "arrived_at", nullable = false, updatable = false)
    private Instant arrivedAt;

    @Column(name = "left_at")
    private Instant leftAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PatientVisitStatus status = PatientVisitStatus.CHECKED_IN;

    protected PatientVisit() {
    }

    public PatientVisit(Patient patient, Appointment appointment) {
        this.patient = Objects.requireNonNull(patient);
        this.appointment = appointment;
    }

    @PrePersist
    private void initializeArrivedAt() {
        if (arrivedAt == null) {
            arrivedAt = Instant.now();
        }
    }

    public void start() {
        if (status == PatientVisitStatus.CHECKED_IN) {
            status = PatientVisitStatus.IN_PROGRESS;
        }
    }

    public void complete() {
        status = PatientVisitStatus.COMPLETED;
        leftAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Appointment getAppointment() {
        return appointment;
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
