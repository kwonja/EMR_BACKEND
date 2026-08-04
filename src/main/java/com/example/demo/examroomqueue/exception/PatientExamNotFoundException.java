package com.example.demo.examroomqueue.exception;

public class PatientExamNotFoundException extends RuntimeException {

    public PatientExamNotFoundException(Long id) {
        super("해당 환자 검사항목이 없습니다: " + id);
    }
}
