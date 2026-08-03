package com.example.demo.staff.exception;

public class InvalidStaffTypeException extends RuntimeException {

    public InvalidStaffTypeException(String staffType) {
        super("잘못된 직원 타입입니다: " + staffType);
    }
}
