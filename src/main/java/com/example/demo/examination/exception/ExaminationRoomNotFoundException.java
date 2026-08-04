package com.example.demo.examination.exception;

public class ExaminationRoomNotFoundException extends RuntimeException {

    public ExaminationRoomNotFoundException(Long id) {
        super("해당 검사실이 없습니다: " + id);
    }
}
