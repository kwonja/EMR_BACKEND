package com.example.demo.patientexam.service;

import com.example.demo.examination.domain.ExamCatalog;
import com.example.demo.examination.exception.ExamCatalogNotFoundException;
import com.example.demo.examination.repository.ExamCatalogRepository;
import com.example.demo.patientexam.domain.PatientExam;
import com.example.demo.patientexam.domain.PatientExamStatus;
import com.example.demo.patientexam.dto.PatientExamCreateItemRequest;
import com.example.demo.patientexam.dto.PatientExamCreateRequest;
import com.example.demo.patientexam.dto.PatientExamResponse;
import com.example.demo.patientexam.exception.DuplicatePatientExamSequenceException;
import com.example.demo.patientexam.exception.InvalidPatientExamRequestException;
import com.example.demo.patientexam.exception.PatientVisitNotFoundException;
import com.example.demo.patientexam.repository.PatientExamRepository;
import com.example.demo.patientvisit.domain.PatientVisit;
import com.example.demo.patientvisit.repository.PatientVisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PatientExamService {

    private final PatientExamRepository patientExamRepository;
    private final PatientVisitRepository patientVisitRepository;
    private final ExamCatalogRepository examCatalogRepository;

    public PatientExamService(
            PatientExamRepository patientExamRepository,
            PatientVisitRepository patientVisitRepository,
            ExamCatalogRepository examCatalogRepository
    ) {
        this.patientExamRepository = patientExamRepository;
        this.patientVisitRepository = patientVisitRepository;
        this.examCatalogRepository = examCatalogRepository;
    }

    @Transactional
    public List<PatientExamResponse> create(
            Long patientVisitId,
            PatientExamCreateRequest request
    ) {
        PatientVisit patientVisit = patientVisitRepository
                .findById(patientVisitId)
                .orElseThrow(() -> new PatientVisitNotFoundException(
                        patientVisitId
                ));

        validateRequest(patientVisitId, request);

        List<PatientExamCreateItemRequest> requestItems =
                new ArrayList<>(request.getExams());
        requestItems.sort(Comparator.comparing(
                PatientExamCreateItemRequest::getSequenceNumber
        ));

        List<PatientExam> patientExams = new ArrayList<>();

        for (PatientExamCreateItemRequest item : requestItems) {
            int sequenceNumber = item.getSequenceNumber();

            if (patientExamRepository
                    .existsByPatientVisit_IdAndSequenceNumber(
                            patientVisitId,
                            sequenceNumber
                    )) {
                throw new DuplicatePatientExamSequenceException(
                        patientVisitId,
                        sequenceNumber
                );
            }

            ExamCatalog examCatalog = examCatalogRepository
                    .findById(item.getExamCatalogId())
                    .orElseThrow(() -> new ExamCatalogNotFoundException(
                            item.getExamCatalogId()
                    ));

            if (!examCatalog.isActive()) {
                throw new InvalidPatientExamRequestException(
                        "비활성화된 검사항목은 배정할 수 없습니다: "
                                + examCatalog.getId()
                );
            }

            if (!examCatalog.getExaminationRoom().isActive()) {
                throw new InvalidPatientExamRequestException(
                        "비활성화된 검사실의 검사항목은 배정할 수 없습니다: "
                                + examCatalog.getId()
                );
            }

            patientExams.add(new PatientExam(
                    patientVisit,
                    examCatalog,
                    sequenceNumber
            ));
        }

        List<PatientExam> savedPatientExams =
                patientExamRepository.saveAllAndFlush(patientExams);
        List<PatientExamResponse> responses = new ArrayList<>();

        for (PatientExam patientExam : savedPatientExams) {
            responses.add(PatientExamResponse.from(patientExam));
        }

        return responses;
    }

    public List<PatientExamResponse> findAll(
            Long patientVisitId,
            PatientExamStatus status
    ) {
        if (!patientVisitRepository.existsById(patientVisitId)) {
            throw new PatientVisitNotFoundException(patientVisitId);
        }

        List<PatientExam> patientExams = patientExamRepository
                .findAllWithDetailsByPatientVisitId(
                        patientVisitId,
                        status
                );
        List<PatientExamResponse> responses = new ArrayList<>();

        for (PatientExam patientExam : patientExams) {
            responses.add(PatientExamResponse.from(patientExam));
        }

        return responses;
    }

    private void validateRequest(
            Long patientVisitId,
            PatientExamCreateRequest request
    ) {
        if (request == null
                || request.getExams() == null
                || request.getExams().isEmpty()) {
            throw new InvalidPatientExamRequestException(
                    "하나 이상의 검사항목이 필요합니다"
            );
        }

        Set<Integer> sequenceNumbers = new HashSet<>();

        for (PatientExamCreateItemRequest item : request.getExams()) {
            if (item == null || item.getExamCatalogId() == null) {
                throw new InvalidPatientExamRequestException(
                        "검사항목 ID가 필요합니다"
                );
            }

            if (item.getSequenceNumber() == null
                    || item.getSequenceNumber() <= 0) {
                throw new InvalidPatientExamRequestException(
                        "검진 순서는 1 이상의 숫자여야 합니다"
                );
            }

            if (!sequenceNumbers.add(item.getSequenceNumber())) {
                throw new DuplicatePatientExamSequenceException(
                        patientVisitId,
                        item.getSequenceNumber()
                );
            }
        }
    }
}
