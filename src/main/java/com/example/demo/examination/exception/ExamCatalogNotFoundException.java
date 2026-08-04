package com.example.demo.examination.exception;

public class ExamCatalogNotFoundException extends RuntimeException {

    public ExamCatalogNotFoundException(Long id) {
        super("해당 검사항목이 없습니다: " + id);
    }
}
