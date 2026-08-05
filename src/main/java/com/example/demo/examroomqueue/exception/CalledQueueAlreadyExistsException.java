package com.example.demo.examroomqueue.exception;

public class CalledQueueAlreadyExistsException extends RuntimeException {

    public CalledQueueAlreadyExistsException(Long examinationRoomId) {
        super("해당 검사실에 이미 호출 중인 환자가 있습니다: "
                + examinationRoomId);
    }
}
