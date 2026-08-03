package com.example.demo.appointment.exception;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(Long id) {
        super("해당 예약이 없습니다: " + id);
    }
}
