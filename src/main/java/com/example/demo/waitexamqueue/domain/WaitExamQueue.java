package com.example.demo.waitexamqueue.domain;

import com.example.demo.examination.domain.ExamOrderItem;
import com.example.demo.examination.domain.ExaminationRoom;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "wait_exam_queues")
public class WaitExamQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_visit_id", nullable = false)
    private PatientVisit patientVisit;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "examination_room_id", nullable = false)
    private ExaminationRoom examinationRoom;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "exam_order_item_id", nullable = false)
    private ExamOrderItem examOrderItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_staff_id")
    private Staff assignedStaff;

    @Column(name = "queue_number", nullable = false)
    private int queueNumber;

    @Column(nullable = false)
    private int priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WaitExamQueueStatus status = WaitExamQueueStatus.WAITING;

    @Column(name = "queued_at", nullable = false, updatable = false)
    private Instant queuedAt;

    @Column(name = "called_at")
    private Instant calledAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    protected WaitExamQueue() {
    }

    public WaitExamQueue(
            PatientVisit patientVisit,
            ExaminationRoom examinationRoom,
            ExamOrderItem examOrderItem,
            int queueNumber,
            int priority
    ) {
        this.patientVisit = Objects.requireNonNull(patientVisit);
        this.examinationRoom = Objects.requireNonNull(examinationRoom);
        this.examOrderItem = Objects.requireNonNull(examOrderItem);
        this.queueNumber = queueNumber;
        this.priority = priority;
    }

    @PrePersist
    private void initializeQueuedAt() {
        if (queuedAt == null) {
            queuedAt = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public PatientVisit getPatientVisit() {
        return patientVisit;
    }

    public ExaminationRoom getExaminationRoom() {
        return examinationRoom;
    }

    public ExamOrderItem getExamOrderItem() {
        return examOrderItem;
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

    public WaitExamQueueStatus getStatus() {
        return status;
    }

    public Instant getQueuedAt() {
        return queuedAt;
    }

    public Instant getCalledAt() {
        return calledAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }
}
