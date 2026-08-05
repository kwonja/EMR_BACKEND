package com.example.demo.patientvisit.service;

import com.example.demo.appointment.domain.Appointment;
import com.example.demo.appointment.exception.AppointmentNotFoundException;
import com.example.demo.appointment.repository.AppointmentRepository;
import com.example.demo.patient.domain.Patient;
import com.example.demo.patient.exception.PatientNotFoundException;
import com.example.demo.patient.repository.PatientRepository;
import com.example.demo.patientexam.domain.PatientExamStatus;
import com.example.demo.patientexam.domain.PatientExam;
import com.example.demo.patientexam.dto.PatientExamResponse;
import com.example.demo.patientexam.exception.PatientVisitNotFoundException;
import com.example.demo.patientexam.repository.PatientExamRepository;
import com.example.demo.patientvisit.domain.PatientVisit;
import com.example.demo.patientvisit.dto.PatientVisitCreateRequest;
import com.example.demo.patientvisit.dto.PatientVisitProgressResponse;
import com.example.demo.patientvisit.dto.PatientVisitResponse;
import com.example.demo.patientvisit.exception.IncompletePatientVisitExamsException;
import com.example.demo.patientvisit.exception.InvalidPatientVisitAppointmentException;
import com.example.demo.patientvisit.repository.PatientVisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientVisitService {

    private final PatientVisitRepository patientVisitRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientExamRepository patientExamRepository;

    public PatientVisitService(
            PatientVisitRepository patientVisitRepository,
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository,
            PatientExamRepository patientExamRepository
    ) {
        this.patientVisitRepository = patientVisitRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientExamRepository = patientExamRepository;
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

    public PatientVisitResponse findById(Long patientVisitId) {
        PatientVisit patientVisit = findPatientVisit(patientVisitId);

        return PatientVisitResponse.from(patientVisit);
    }

    public PatientVisitProgressResponse findProgress(Long patientVisitId) {
        PatientVisit patientVisit = findPatientVisit(patientVisitId);
        List<PatientExam> patientExams = patientExamRepository
                .findAllWithDetailsByPatientVisitId(patientVisitId, null);
        List<PatientExamResponse> examResponses = new ArrayList<>();
        PatientExamResponse inProgressExam = null;
        PatientExamResponse waitingExam = null;
        int completedExamCount = 0;

        for (PatientExam patientExam : patientExams) {
            PatientExamResponse response = PatientExamResponse.from(patientExam);
            examResponses.add(response);

            if (patientExam.getStatus() == PatientExamStatus.COMPLETED) {
                completedExamCount++;
            }

            if (inProgressExam == null
                    && patientExam.getStatus() == PatientExamStatus.IN_PROGRESS) {
                inProgressExam = response;
            }

            if (waitingExam == null
                    && patientExam.getStatus() == PatientExamStatus.WAITING) {
                waitingExam = response;
            }
        }

        PatientExamResponse currentExam = inProgressExam != null
                ? inProgressExam
                : waitingExam;

        return PatientVisitProgressResponse.of(
                patientVisit,
                completedExamCount,
                currentExam,
                examResponses
        );
    }

    @Transactional
    public PatientVisitResponse complete(Long patientVisitId) {
        PatientVisit patientVisit = findPatientVisit(patientVisitId);

        if (patientExamRepository.countByPatientVisit_Id(
                patientVisitId
        ) == 0) {
            throw new IncompletePatientVisitExamsException(
                    "등록된 검사가 없어 방문을 완료할 수 없습니다"
            );
        }

        if (patientExamRepository.existsByPatientVisit_IdAndStatusNot(
                patientVisitId,
                PatientExamStatus.COMPLETED
        )) {
            throw new IncompletePatientVisitExamsException(
                    "모든 검사가 완료되어야 방문을 완료할 수 있습니다"
            );
        }

        patientVisit.complete();
        PatientVisit savedPatientVisit = patientVisitRepository
                .saveAndFlush(patientVisit);

        return PatientVisitResponse.from(savedPatientVisit);
    }

    private PatientVisit findPatientVisit(Long patientVisitId) {
        return patientVisitRepository.findById(patientVisitId)
                .orElseThrow(() -> new PatientVisitNotFoundException(
                        patientVisitId
                ));
    }
}
