package com.example.demo.examroomqueue.domain;

import com.example.demo.patientexam.domain.PatientExam;

import java.time.Instant;

public class ExamRoomQueueEvent {

    private final ExamRoomQueueEventType eventType;
    private final Long examinationRoomId;
    private final Long queueId;
    private final Long patientExamId;
    private final Long patientVisitId;
    private final int queueNumber;
    private final ExamRoomQueueStatus status;
    private final Instant occurredAt;

    public ExamRoomQueueEvent(
            ExamRoomQueueEventType eventType,
            Long examinationRoomId,
            Long queueId,
            Long patientExamId,
            Long patientVisitId,
            int queueNumber,
            ExamRoomQueueStatus status
    ) {
        this.eventType = eventType;
        this.examinationRoomId = examinationRoomId;
        this.queueId = queueId;
        this.patientExamId = patientExamId;
        this.patientVisitId = patientVisitId;
        this.queueNumber = queueNumber;
        this.status = status;
        this.occurredAt = Instant.now();
    }

    public ExamRoomQueueEventType getEventType() {
        return eventType;
    }

    public Long getExaminationRoomId() {
        return examinationRoomId;
    }

    public Long getQueueId() {
        return queueId;
    }

    public Long getPatientExamId() {
        return patientExamId;
    }

    public Long getPatientVisitId() {
        return patientVisitId;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public ExamRoomQueueStatus getStatus() {
        return status;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public static ExamRoomQueueEvent from(
            ExamRoomQueueEventType eventType,
            ExamRoomQueue queue
    ) {
        PatientExam patientExam = queue.getPatientExam();

        return new ExamRoomQueueEvent(
                eventType,
                patientExam
                        .getExamCatalog()
                        .getExaminationRoom()
                        .getId(),
                queue.getId(),
                patientExam.getId(),
                patientExam.getPatientVisit().getId(),
                queue.getQueueNumber(),
                queue.getStatus()
        );
    }
}
