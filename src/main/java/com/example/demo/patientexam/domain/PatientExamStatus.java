package com.example.demo.patientexam.domain;

public enum PatientExamStatus {
    PENDING, //검사는 등록됬지만, 대기열에 등록되지 않는다.
    WAITING,
    IN_PROGRESS,
    COMPLETED,
    ON_HOLD,
    CANCELLED
}
