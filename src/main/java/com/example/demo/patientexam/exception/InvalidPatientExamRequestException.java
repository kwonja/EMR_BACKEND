package com.example.demo.patientexam.exception;

public class InvalidPatientExamRequestException extends RuntimeException {

    public InvalidPatientExamRequestException(String message) {
        super(message);
    }
}
