package com.example.demo.department.exception;

public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(Long id) {
        super("해당 진료과가 없습니다: " + id);
    }
}
