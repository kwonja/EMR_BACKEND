package com.example.demo.examination.domain;

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
@Table(name = "exam_order_items")
public class ExamOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "exam_order_id", nullable = false)
    private ExamOrder examOrder;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "exam_catalog_id", nullable = false)
    private ExamCatalog examCatalog;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ExamOrderItemStatus status = ExamOrderItemStatus.ORDERED;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    protected ExamOrderItem() {
    }

    public ExamOrderItem(
            ExamOrder examOrder,
            ExamCatalog examCatalog
    ) {
        this.examOrder = Objects.requireNonNull(examOrder);
        this.examCatalog = Objects.requireNonNull(examCatalog);
    }

    public Long getId() {
        return id;
    }

    public ExamOrder getExamOrder() {
        return examOrder;
    }

    public ExamCatalog getExamCatalog() {
        return examCatalog;
    }

    public ExamOrderItemStatus getStatus() {
        return status;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }
}
