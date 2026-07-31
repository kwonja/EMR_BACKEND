package com.example.demo.patient.exception;

public class DuplicatePatientNumberException extends RuntimeException {

    public DuplicatePatientNumberException(String patientNumber) {
        super("이미 등록된 환자번호입니다: " + patientNumber);
    }
}
