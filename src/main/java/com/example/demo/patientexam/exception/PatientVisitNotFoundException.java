package com.example.demo.patientexam.exception;

public class PatientVisitNotFoundException extends RuntimeException {

    public PatientVisitNotFoundException(Long id) {
        super("해당 환자 방문 기록이 없습니다: " + id);
    }
}
