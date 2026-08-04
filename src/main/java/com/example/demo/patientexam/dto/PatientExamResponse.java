package com.example.demo.patientexam.dto;

import com.example.demo.examination.domain.ExamCatalog;
import com.example.demo.examination.domain.ExaminationRoom;
import com.example.demo.patientexam.domain.PatientExam;
import com.example.demo.patientexam.domain.PatientExamStatus;

import java.time.Instant;

public class PatientExamResponse {

    private final Long id;
    private final Long patientVisitId;
    private final Long examCatalogId;
    private final String examCode;
    private final String examName;
    private final Long examinationRoomId;
    private final String roomName;
    private final String roomNo;
    private final String location;
    private final int sequenceNumber;
    private final PatientExamStatus status;
    private final String holdReason;
    private final Instant startedAt;
    private final Instant completedAt;

    public PatientExamResponse(
            Long id,
            Long patientVisitId,
            Long examCatalogId,
            String examCode,
            String examName,
            Long examinationRoomId,
            String roomName,
            String roomNo,
            String location,
            int sequenceNumber,
            PatientExamStatus status,
            String holdReason,
            Instant startedAt,
            Instant completedAt
    ) {
        this.id = id;
        this.patientVisitId = patientVisitId;
        this.examCatalogId = examCatalogId;
        this.examCode = examCode;
        this.examName = examName;
        this.examinationRoomId = examinationRoomId;
        this.roomName = roomName;
        this.roomNo = roomNo;
        this.location = location;
        this.sequenceNumber = sequenceNumber;
        this.status = status;
        this.holdReason = holdReason;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
    }

    public static PatientExamResponse from(PatientExam patientExam) {
        ExamCatalog examCatalog = patientExam.getExamCatalog();
        ExaminationRoom examinationRoom = examCatalog.getExaminationRoom();

        return new PatientExamResponse(
                patientExam.getId(),
                patientExam.getPatientVisit().getId(),
                examCatalog.getId(),
                examCatalog.getCode(),
                examCatalog.getName(),
                examinationRoom.getId(),
                examinationRoom.getName(),
                examinationRoom.getRoomNo(),
                examinationRoom.getLocation(),
                patientExam.getSequenceNumber(),
                patientExam.getStatus(),
                patientExam.getHoldReason(),
                patientExam.getStartedAt(),
                patientExam.getCompletedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getPatientVisitId() {
        return patientVisitId;
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

    public int getSequenceNumber() {
        return sequenceNumber;
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
}
