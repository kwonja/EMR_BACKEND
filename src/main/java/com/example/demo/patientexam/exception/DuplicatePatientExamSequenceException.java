package com.example.demo.patientexam.exception;

public class DuplicatePatientExamSequenceException extends RuntimeException {

    public DuplicatePatientExamSequenceException(
            Long patientVisitId,
            int sequenceNumber
    ) {
        super(
                "이미 사용 중인 검진 순서입니다: 방문 "
                        + patientVisitId
                        + ", 순서 "
                        + sequenceNumber
        );
    }
}
