package com.example.demo.examination.domain;

import com.example.demo.consultation.domain.Consultation;
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
@Table(name = "exam_orders")
public class ExamOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ordered_by_staff_id", nullable = false)
    private Staff orderedByStaff;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ExamOrderStatus status = ExamOrderStatus.ORDERED;

    @Column(name = "ordered_at", nullable = false, updatable = false)
    private Instant orderedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    protected ExamOrder() {
    }

    public ExamOrder(
            Consultation consultation,
            Staff orderedByStaff
    ) {
        this.consultation = Objects.requireNonNull(consultation);
        this.orderedByStaff = Objects.requireNonNull(orderedByStaff);
    }

    @PrePersist
    private void initializeOrderedAt() {
        if (orderedAt == null) {
            orderedAt = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public Staff getOrderedByStaff() {
        return orderedByStaff;
    }

    public ExamOrderStatus getStatus() {
        return status;
    }

    public Instant getOrderedAt() {
        return orderedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }
}
