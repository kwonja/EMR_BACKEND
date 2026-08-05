package com.example.demo.examroomqueue.dto;

import com.example.demo.examroomqueue.domain.ExamRoomQueue;
import com.example.demo.examroomqueue.domain.ExamRoomQueueStatus;
import com.example.demo.examination.domain.ExamCatalog;
import com.example.demo.examination.domain.ExaminationRoom;
import com.example.demo.patient.domain.Patient;
import com.example.demo.patientexam.domain.PatientExam;
import com.example.demo.patientexam.domain.PatientExamStatus;
import com.example.demo.staff.domain.Staff;

import java.time.Instant;

public class ExamRoomQueueResponse {

    private final Long queueId;
    private final Long patientExamId;
    private final PatientExamStatus patientExamStatus;
    private final Instant examStartedAt;
    private final Instant examCompletedAt;
    private final Long patientVisitId;
    private final Long patientId;
    private final String patientName;
    private final Long examCatalogId;
    private final String examCode;
    private final String examName;
    private final Long examinationRoomId;
    private final String roomName;
    private final String roomNo;
    private final String location;
    private final Long assignedStaffId;
    private final String assignedStaffName;
    private final int queueNumber;
    private final ExamRoomQueueStatus status;
    private final Instant queuedAt;
    private final Instant calledAt;
    private final Instant enteredAt;
    private final Instant exitedAt;

    public ExamRoomQueueResponse(
            Long queueId,
            Long patientExamId,
            PatientExamStatus patientExamStatus,
            Instant examStartedAt,
            Instant examCompletedAt,
            Long patientVisitId,
            Long patientId,
            String patientName,
            Long examCatalogId,
            String examCode,
            String examName,
            Long examinationRoomId,
            String roomName,
            String roomNo,
            String location,
            Long assignedStaffId,
            String assignedStaffName,
            int queueNumber,
            ExamRoomQueueStatus status,
            Instant queuedAt,
            Instant calledAt,
            Instant enteredAt,
            Instant exitedAt
    ) {
        this.queueId = queueId;
        this.patientExamId = patientExamId;
        this.patientExamStatus = patientExamStatus;
        this.examStartedAt = examStartedAt;
        this.examCompletedAt = examCompletedAt;
        this.patientVisitId = patientVisitId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.examCatalogId = examCatalogId;
        this.examCode = examCode;
        this.examName = examName;
        this.examinationRoomId = examinationRoomId;
        this.roomName = roomName;
        this.roomNo = roomNo;
        this.location = location;
        this.assignedStaffId = assignedStaffId;
        this.assignedStaffName = assignedStaffName;
        this.queueNumber = queueNumber;
        this.status = status;
        this.queuedAt = queuedAt;
        this.calledAt = calledAt;
        this.enteredAt = enteredAt;
        this.exitedAt = exitedAt;
    }

    public static ExamRoomQueueResponse from(ExamRoomQueue queue) {
        PatientExam patientExam = queue.getPatientExam();
        Patient patient = patientExam.getPatientVisit().getPatient();
        ExamCatalog examCatalog = patientExam.getExamCatalog();
        ExaminationRoom room = examCatalog.getExaminationRoom();
        Staff assignedStaff = queue.getAssignedStaff();

        return new ExamRoomQueueResponse(
                queue.getId(),
                patientExam.getId(),
                patientExam.getStatus(),
                patientExam.getStartedAt(),
                patientExam.getCompletedAt(),
                patientExam.getPatientVisit().getId(),
                patient.getId(),
                patient.getName(),
                examCatalog.getId(),
                examCatalog.getCode(),
                examCatalog.getName(),
                room.getId(),
                room.getName(),
                room.getRoomNo(),
                room.getLocation(),
                assignedStaff == null ? null : assignedStaff.getId(),
                assignedStaff == null ? null : assignedStaff.getName(),
                queue.getQueueNumber(),
                queue.getStatus(),
                queue.getQueuedAt(),
                queue.getCalledAt(),
                queue.getEnteredAt(),
                queue.getExitedAt()
        );
    }

    public Long getQueueId() {
        return queueId;
    }

    public Long getPatientExamId() {
        return patientExamId;
    }

    public PatientExamStatus getPatientExamStatus() {
        return patientExamStatus;
    }

    public Instant getExamStartedAt() {
        return examStartedAt;
    }

    public Instant getExamCompletedAt() {
        return examCompletedAt;
    }

    public Long getPatientVisitId() {
        return patientVisitId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public Long getExamCatalogId() {
        return examCatalogId;
    }

    public String getExamCode() {
        return examCode;
    }

    public String getExamName() {
        return examName;
    }

    public Long getExaminationRoomId() {
        return examinationRoomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public String getLocation() {
        return location;
    }

    public Long getAssignedStaffId() {
        return assignedStaffId;
    }

    public String getAssignedStaffName() {
        return assignedStaffName;
    }

    public int getQueueNumber() {
        return queueNumber;
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

    public Instant getExitedAt() {
        return exitedAt;
    }
}
