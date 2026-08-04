package com.example.demo.examroomqueue.exception;

public class DuplicateActiveExamRoomQueueException extends RuntimeException {

    public DuplicateActiveExamRoomQueueException(Long patientExamId) {
        super("이미 활성 대기열에 등록된 검사항목입니다: " + patientExamId);
    }
}
