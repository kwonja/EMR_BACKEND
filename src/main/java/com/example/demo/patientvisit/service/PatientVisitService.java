package com.example.demo.patientvisit.service;

import com.example.demo.patient.domain.Patient;
import com.example.demo.patient.exception.PatientNotFoundException;
import com.example.demo.patient.repository.PatientRepository;
import com.example.demo.patientvisit.domain.PatientVisit;
import com.example.demo.patientvisit.dto.PatientVisitCreateRequest;
import com.example.demo.patientvisit.dto.PatientVisitResponse;
import com.example.demo.patientvisit.repository.PatientVisitRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientVisitService {

    private final PatientVisitRepository patientVisitRepository;
    private final PatientRepository patientRepository;

    public PatientVisitService(
            PatientVisitRepository patientVisitRepository,
            PatientRepository patientRepository
    ) {
        this.patientVisitRepository = patientVisitRepository;
        this.patientRepository = patientRepository;
    }

    public PatientVisitResponse create(PatientVisitCreateRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(
                        request.getPatientId()
                ));

        PatientVisit patientVisit = new PatientVisit(patient);
        PatientVisit savedPatientVisit = patientVisitRepository.save(patientVisit);

        return PatientVisitResponse.from(savedPatientVisit);
    }
}
