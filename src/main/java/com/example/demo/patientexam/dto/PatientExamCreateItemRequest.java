package com.example.demo.patientexam.dto;

public class PatientExamCreateItemRequest {

    private Long examCatalogId;
    private Integer sequenceNumber;

    public PatientExamCreateItemRequest() {
    }

    public Long getExamCatalogId() {
        return examCatalogId;
    }

    public void setExamCatalogId(Long examCatalogId) {
        this.examCatalogId = examCatalogId;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
