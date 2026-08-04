package com.example.demo.patientvisit.service;

import com.example.demo.appointment.domain.Appointment;
import com.example.demo.appointment.exception.AppointmentNotFoundException;
import com.example.demo.appointment.repository.AppointmentRepository;
import com.example.demo.patient.domain.Patient;
import com.example.demo.patient.exception.PatientNotFoundException;
import com.example.demo.patient.repository.PatientRepository;
import com.example.demo.patientvisit.domain.PatientVisit;
import com.example.demo.patientvisit.dto.PatientVisitCreateRequest;
import com.example.demo.patientvisit.dto.PatientVisitResponse;
import com.example.demo.patientvisit.exception.InvalidPatientVisitAppointmentException;
import com.example.demo.patientvisit.repository.PatientVisitRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientVisitService {

    private final PatientVisitRepository patientVisitRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public PatientVisitService(
            PatientVisitRepository patientVisitRepository,
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository
    ) {
        this.patientVisitRepository = patientVisitRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public PatientVisitResponse create(PatientVisitCreateRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(
                        request.getPatientId()
                ));

        Appointment appointment = null;

        if (request.getAppointmentId() != null) {
            appointment = appointmentRepository
                    .findById(request.getAppointmentId())
                    .orElseThrow(() -> new AppointmentNotFoundException(
                            request.getAppointmentId()
                    ));

            if (!appointment.getPatient().getId().equals(patient.getId())) {
                throw new InvalidPatientVisitAppointmentException();
            }
        }

        PatientVisit patientVisit = new PatientVisit(patient, appointment);
        PatientVisit savedPatientVisit = patientVisitRepository.save(patientVisit);

        return PatientVisitResponse.from(savedPatientVisit);
    }
}
