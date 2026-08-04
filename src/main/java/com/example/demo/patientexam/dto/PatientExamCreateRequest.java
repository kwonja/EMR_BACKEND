package com.example.demo.patientexam.dto;

import java.util.List;

public class PatientExamCreateRequest {

    private List<PatientExamCreateItemRequest> exams;

    public PatientExamCreateRequest() {
    }

    public List<PatientExamCreateItemRequest> getExams() {
        return exams;
    }

    public void setExams(List<PatientExamCreateItemRequest> exams) {
        this.exams = exams;
    }
}
