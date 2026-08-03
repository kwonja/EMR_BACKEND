package com.example.demo.appointment.exception;

public class InvalidAppointmentStatusException extends RuntimeException {

    public InvalidAppointmentStatusException(String status) {
        super("잘못된 예약 상태입니다: " + status);
    }
}
