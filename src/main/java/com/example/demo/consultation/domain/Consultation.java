package com.example.demo.consultation.domain;

import com.example.demo.department.domain.Department;
import com.example.demo.patientvisit.domain.PatientVisit;
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
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "consultations")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_visit_id", nullable = false)
    private PatientVisit patientVisit;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ConsultationStatus status = ConsultationStatus.PLANNED;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    protected Consultation() {
    }

    public Consultation(
            PatientVisit patientVisit,
            Department department,
            Staff staff
    ) {
        this.patientVisit = Objects.requireNonNull(patientVisit);
        this.department = Objects.requireNonNull(department);
        this.staff = Objects.requireNonNull(staff);
    }

    public Long getId() {
        return id;
    }

    public PatientVisit getPatientVisit() {
        return patientVisit;
    }

    public Department getDepartment() {
        return department;
    }

    public Staff getStaff() {
        return staff;
    }

    public ConsultationStatus getStatus() {
        return status;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }
}
