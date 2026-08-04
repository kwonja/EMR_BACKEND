package com.example.demo.patientvisit.exception;

public class InvalidPatientVisitAppointmentException extends RuntimeException {

    public InvalidPatientVisitAppointmentException() {
        super("예약 환자와 방문 환자가 일치하지 않습니다");
    }
}
