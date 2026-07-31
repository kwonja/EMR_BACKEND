package com.example.demo.patient.service;

import com.example.demo.patient.domain.Patient;
import com.example.demo.patient.dto.PatientCreateRequest;
import com.example.demo.patient.dto.PatientCreateResponse;
import com.example.demo.patient.exception.DuplicatePatientNumberException;
import com.example.demo.patient.repository.PatientRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientCreateResponse create(PatientCreateRequest request) {

        Patient patient = new Patient(
                request.getPatientNumber(),
                request.getName(),
                request.getBirthDate(),
                request.getPhone()
        );

        try {
            Patient savedPatient = patientRepository.saveAndFlush(patient);
            return PatientCreateResponse.from(savedPatient);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicatePatientNumberException(
                    request.getPatientNumber()
            );
        }
    }
}
