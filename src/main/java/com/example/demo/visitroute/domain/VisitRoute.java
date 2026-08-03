package com.example.demo.visitroute.domain;

import com.example.demo.consultation.domain.Consultation;
import com.example.demo.department.domain.Department;
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
@Table(name = "visit_routes")
public class VisitRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_visit_id", nullable = false)
    private PatientVisit patientVisit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_consultation_id")
    private Consultation sourceConsultation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target_consultation_id")
    private Consultation targetConsultation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_order_item_id")
    private ExamOrderItem examOrderItem;

    @Enumerated(EnumType.STRING)
    @Column(name = "route_type", nullable = false, length = 30)
    private VisitRouteType routeType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target_department_id")
    private Department targetDepartment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target_staff_id")
    private Staff targetStaff;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target_examination_room_id")
    private ExaminationRoom targetExaminationRoom;

    @Column(name = "sequence_number", nullable = false)
    private int sequenceNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private VisitRouteStatus status = VisitRouteStatus.PENDING;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructed_by_staff_id")
    private Staff instructedByStaff;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "arrived_at")
    private Instant arrivedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    protected VisitRoute() {
    }

    public VisitRoute(
            PatientVisit patientVisit,
            Consultation sourceConsultation,
            Consultation targetConsultation,
            ExamOrderItem examOrderItem,
            VisitRouteType routeType,
            Department targetDepartment,
            Staff targetStaff,
            ExaminationRoom targetExaminationRoom,
            int sequenceNumber,
            Staff instructedByStaff
    ) {
        this.patientVisit = Objects.requireNonNull(patientVisit);
        this.sourceConsultation = sourceConsultation;
        this.targetConsultation = targetConsultation;
        this.examOrderItem = examOrderItem;
        this.routeType = Objects.requireNonNull(routeType);
        this.targetDepartment = targetDepartment;
        this.targetStaff = targetStaff;
        this.targetExaminationRoom = targetExaminationRoom;
        this.sequenceNumber = sequenceNumber;
        this.instructedByStaff = instructedByStaff;
    }

    @PrePersist
    private void initializeCreatedAt() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public PatientVisit getPatientVisit() {
        return patientVisit;
    }

    public Consultation getSourceConsultation() {
        return sourceConsultation;
    }

    public Consultation getTargetConsultation() {
        return targetConsultation;
    }

    public ExamOrderItem getExamOrderItem() {
        return examOrderItem;
    }

    public VisitRouteType getRouteType() {
        return routeType;
    }

    public Department getTargetDepartment() {
        return targetDepartment;
    }

    public Staff getTargetStaff() {
        return targetStaff;
    }

    public ExaminationRoom getTargetExaminationRoom() {
        return targetExaminationRoom;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public VisitRouteStatus getStatus() {
        return status;
    }

    public Staff getInstructedByStaff() {
        return instructedByStaff;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getArrivedAt() {
        return arrivedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }
}
