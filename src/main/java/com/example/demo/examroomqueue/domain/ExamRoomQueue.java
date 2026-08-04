package com.example.demo.examroomqueue.domain;

import com.example.demo.patientexam.domain.PatientExam;
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
import java.util.Objects;

@Entity
@Table(name = "exam_room_queues")
public class ExamRoomQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_exam_id", nullable = false)
    private PatientExam patientExam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_staff_id")
    private Staff assignedStaff;

    @Column(name = "queue_number", nullable = false)
    private int queueNumber;

    @Column(nullable = false)
    private int priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ExamRoomQueueStatus status = ExamRoomQueueStatus.WAITING;

    @Column(name = "queued_at", nullable = false, updatable = false)
    private Instant queuedAt;

    @Column(name = "called_at")
    private Instant calledAt;

    @Column(name = "entered_at")
    private Instant enteredAt;

    protected ExamRoomQueue() {
    }

    public ExamRoomQueue(
            PatientExam patientExam,
            int queueNumber,
            int priority
    ) {
        this.patientExam = Objects.requireNonNull(patientExam);
        this.queueNumber = queueNumber;
        this.priority = priority;
    }

    @PrePersist
    private void initializeQueuedAt() {
        if (queuedAt == null) {
            queuedAt = Instant.now();
        }
    }

    public void assignStaff(Staff assignedStaff) {
        this.assignedStaff = Objects.requireNonNull(assignedStaff);
    }

    public void call() {
        status = ExamRoomQueueStatus.CALLED;
        calledAt = Instant.now();
    }

    public void enter() {
        status = ExamRoomQueueStatus.ENTERED;
        enteredAt = Instant.now();
    }

    public void cancel() {
        status = ExamRoomQueueStatus.CANCELLED;
    }

    public void markNoShow() {
        status = ExamRoomQueueStatus.NO_SHOW;
    }

    public Long getId() {
        return id;
    }

    public PatientExam getPatientExam() {
        return patientExam;
    }

    public Staff getAssignedStaff() {
        return assignedStaff;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public int getPriority() {
        return priority;
    }

    public ExamRoomQueueStatus getStatus() {
        return status;
    }

    public Instant getQueuedAt() {
        return queuedAt;
    }

    public Instant getCalledAt() {
        return calledAt;
    }

    public Instant getEnteredAt() {
        return enteredAt;
    }
}
