package com.example.demo.examroomqueue.exception;

public class ExamRoomQueueNotFoundException extends RuntimeException {

    public ExamRoomQueueNotFoundException(Long id) {
        super("해당 대기열이 없습니다: " + id);
    }
}
