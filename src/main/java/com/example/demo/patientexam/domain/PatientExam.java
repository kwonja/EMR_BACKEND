package com.example.demo.patientexam.domain;

import com.example.demo.examination.domain.ExamCatalog;
import com.example.demo.patientvisit.domain.PatientVisit;
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
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(
        name = "patient_exams",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_patient_exam_sequence",
                columnNames = {"patient_visit_id", "sequence_number"}
        )
)
public class PatientExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_visit_id", nullable = false)
    private PatientVisit patientVisit;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "exam_catalog_id", nullable = false)
    private ExamCatalog examCatalog;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PatientExamStatus status = PatientExamStatus.PENDING;

    @Column(name = "hold_reason", length = 500)
    private String holdReason;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "sequence_number", nullable = false)
    private int sequenceNumber;

    protected PatientExam() {
    }

    public PatientExam(
            PatientVisit patientVisit,
            ExamCatalog examCatalog,
            int sequenceNumber
    ) {
        this.patientVisit = Objects.requireNonNull(patientVisit);
        this.examCatalog = Objects.requireNonNull(examCatalog);
        this.sequenceNumber = sequenceNumber;
    }

    public void waitForExam() {
        status = PatientExamStatus.WAITING;
        holdReason = null;
    }

    public void start() {
        status = PatientExamStatus.IN_PROGRESS;
        startedAt = Instant.now();
    }

    public void complete() {
        status = PatientExamStatus.COMPLETED;
        completedAt = Instant.now();
    }

    public void hold(String reason) {
        status = PatientExamStatus.ON_HOLD;
        holdReason = Objects.requireNonNull(reason);
    }

    public void cancel() {
        status = PatientExamStatus.CANCELLED;
    }

    public Long getId() {
        return id;
    }

    public PatientVisit getPatientVisit() {
        return patientVisit;
    }

    public ExamCatalog getExamCatalog() {
        return examCatalog;
    }

    public PatientExamStatus getStatus() {
        return status;
    }

    public String getHoldReason() {
        return holdReason;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }
}
