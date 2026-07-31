package com.example.demo.patient.dto;

import java.time.LocalDate;

public class PatientCreateRequest {

    private String patientNumber;

    private String name;

    private LocalDate birthDate;

    private String phone;

    public PatientCreateRequest() {
    }

    public PatientCreateRequest(
            String patientNumber,
            String name,
            LocalDate birthDate,
            String phone
    ) {
        this.patientNumber = patientNumber;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
