package com.example.demo.examination.exception;

public class DuplicateExamCodeException extends RuntimeException {

    public DuplicateExamCodeException(String code) {
        super("이미 등록된 검사항목 코드입니다: " + code);
    }
}
