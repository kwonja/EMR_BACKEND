package com.example.demo.patient.service;

import com.example.demo.patient.domain.Patient;
import com.example.demo.patient.dto.PatientCreateRequest;
import com.example.demo.patient.dto.PatientResponse;
import com.example.demo.patient.dto.PatientUpdateRequest;
import com.example.demo.patient.exception.DuplicatePatientNumberException;
import com.example.demo.patient.exception.PatientNotFoundException;
import com.example.demo.patient.repository.PatientRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse create(PatientCreateRequest request) {

        Patient patient = new Patient(
                request.getPatientNumber(),
                request.getName(),
                request.getBirthDate(),
                request.getPhone()
        );

        try {
            Patient savedPatient = patientRepository.saveAndFlush(patient);
            return PatientResponse.from(savedPatient);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicatePatientNumberException(
                    request.getPatientNumber()
            );
        }
    }

    public PatientResponse findById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        return PatientResponse.from(patient);
    }

    public List<PatientResponse> findAll() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponse> responses = new ArrayList<>();

        for (Patient patient : patients) {
            responses.add(PatientResponse.from(patient));
        }

        return responses;
    }

    public PatientResponse update(Long id, PatientUpdateRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        patient.update(
                request.getName(),
                request.getBirthDate(),
                request.getPhone()
        );

        Patient savedPatient = patientRepository.save(patient);
        return PatientResponse.from(savedPatient);
    }
}
