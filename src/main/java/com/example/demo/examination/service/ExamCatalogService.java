package com.example.demo.examination.service;

import com.example.demo.examination.domain.ExamCatalog;
import com.example.demo.examination.domain.ExaminationRoom;
import com.example.demo.examination.dto.ExamCatalogCreateRequest;
import com.example.demo.examination.dto.ExamCatalogResponse;
import com.example.demo.examination.exception.DuplicateExamCodeException;
import com.example.demo.examination.exception.ExaminationRoomNotFoundException;
import com.example.demo.examination.repository.ExamCatalogRepository;
import com.example.demo.examination.repository.ExaminationRoomRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ExamCatalogService {

    private final ExamCatalogRepository examCatalogRepository;
    private final ExaminationRoomRepository examinationRoomRepository;

    public ExamCatalogService(
            ExamCatalogRepository examCatalogRepository,
            ExaminationRoomRepository examinationRoomRepository
    ) {
        this.examCatalogRepository = examCatalogRepository;
        this.examinationRoomRepository = examinationRoomRepository;
    }

    public ExamCatalogResponse create(ExamCatalogCreateRequest request) {
        if (examCatalogRepository.existsByCode(request.getCode())) {
            throw new DuplicateExamCodeException(request.getCode());
        }

        ExaminationRoom examinationRoom = examinationRoomRepository
                .findById(request.getExaminationRoomId())
                .orElseThrow(() -> new ExaminationRoomNotFoundException(
                        request.getExaminationRoomId()
                ));

        ExamCatalog examCatalog = new ExamCatalog(
                examinationRoom,
                request.getCode(),
                request.getName(),
                request.getDescription()
        );

        try {
            ExamCatalog savedExamCatalog =
                    examCatalogRepository.saveAndFlush(examCatalog);

            return ExamCatalogResponse.from(savedExamCatalog);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateExamCodeException(request.getCode());
        }
    }
}
