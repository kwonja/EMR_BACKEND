package com.example.demo.appointment.service;

import com.example.demo.appointment.domain.Appointment;
import com.example.demo.appointment.domain.AppointmentStatus;
import com.example.demo.appointment.dto.AppointmentCreateRequest;
import com.example.demo.appointment.dto.AppointmentResponse;
import com.example.demo.appointment.dto.AppointmentStatusUpdateRequest;
import com.example.demo.appointment.exception.AppointmentNotFoundException;
import com.example.demo.appointment.exception.InvalidAppointmentStatusException;
import com.example.demo.appointment.repository.AppointmentRepository;
import com.example.demo.patient.domain.Patient;
import com.example.demo.patient.exception.PatientNotFoundException;
import com.example.demo.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public AppointmentResponse create(AppointmentCreateRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(
                        request.getPatientId()
                ));

        Appointment appointment = new Appointment(
                patient,
                request.getScheduledAt()
        );

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentResponse.from(savedAppointment);
    }

    public List<AppointmentResponse> findAll() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentResponse> responses = new ArrayList<>();

        for (Appointment appointment : appointments) {
            responses.add(AppointmentResponse.from(appointment));
        }

        return responses;
    }

    public AppointmentResponse findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        return AppointmentResponse.from(appointment);
    }

    public AppointmentResponse updateStatus(
            Long id,
            AppointmentStatusUpdateRequest request
    ) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        AppointmentStatus status;

        try {
            status = AppointmentStatus.valueOf(
                    request.getStatus().toUpperCase()
            );
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new InvalidAppointmentStatusException(request.getStatus());
        }

        appointment.updateStatus(status);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentResponse.from(savedAppointment);
    }

    public void delete(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        appointmentRepository.delete(appointment);
    }
}
