package com.example.demo.patientvisit.dto;

public class PatientVisitCreateRequest {

    private Long patientId;
    private Long appointmentId;

    public PatientVisitCreateRequest() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }
}
