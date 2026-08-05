package com.example.demo.examroomqueue.exception;

public class OutOfOrderPatientExamQueueException extends RuntimeException {

    public OutOfOrderPatientExamQueueException(
            int firstSequenceNumber,
            int requestedSequenceNumber
    ) {
        super("검사를 순서대로 대기열에 추가해주세요. "
                + "다음 검사 순서: "
                + firstSequenceNumber
                + ", 내가 입력한 순서: "
                + requestedSequenceNumber);
    }
}
