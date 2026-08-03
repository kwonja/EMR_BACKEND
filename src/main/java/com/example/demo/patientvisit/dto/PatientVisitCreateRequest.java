package com.example.demo.patientvisit.dto;

public class PatientVisitCreateRequest {

    private Long patientId;

    public PatientVisitCreateRequest() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
