package com.example.demo.patient.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(Long id) {
        super("해당 환자가 없습니다: " + id);
    }
}
