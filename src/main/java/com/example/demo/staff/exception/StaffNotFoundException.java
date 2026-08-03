package com.example.demo.staff.exception;

public class StaffNotFoundException extends RuntimeException {

    public StaffNotFoundException(Long id) {
        super("해당 직원이 없습니다: " + id);
    }
}
