package com.example.demo.examroomqueue.service;

import com.example.demo.examroomqueue.domain.ExamRoomQueue;
import com.example.demo.examroomqueue.domain.ExamRoomQueueStatus;
import com.example.demo.examroomqueue.dto.ExamRoomQueueCreateRequest;
import com.example.demo.examroomqueue.dto.ExamRoomQueueResponse;
import com.example.demo.examroomqueue.exception.DuplicateActiveExamRoomQueueException;
import com.example.demo.examroomqueue.exception.InvalidExamRoomQueueRequestException;
import com.example.demo.examroomqueue.exception.PatientExamNotFoundException;
import com.example.demo.examroomqueue.repository.ExamRoomQueueRepository;
import com.example.demo.examination.domain.ExaminationRoom;
import com.example.demo.patientexam.domain.PatientExam;
import com.example.demo.patientexam.domain.PatientExamStatus;
import com.example.demo.patientexam.repository.PatientExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamRoomQueueService {

    private static final List<ExamRoomQueueStatus> ACTIVE_STATUSES = List.of(
            ExamRoomQueueStatus.WAITING,
            ExamRoomQueueStatus.CALLED
    );

    private final ExamRoomQueueRepository examRoomQueueRepository;
    private final PatientExamRepository patientExamRepository;

    public ExamRoomQueueService(
            ExamRoomQueueRepository examRoomQueueRepository,
            PatientExamRepository patientExamRepository
    ) {
        this.examRoomQueueRepository = examRoomQueueRepository;
        this.patientExamRepository = patientExamRepository;
    }

    @Transactional
    public ExamRoomQueueResponse create(
            ExamRoomQueueCreateRequest request
    ) {
        validateRequest(request);

        PatientExam patientExam = patientExamRepository
                .findByIdWithDetails(request.getPatientExamId())
                .orElseThrow(() -> new PatientExamNotFoundException(
                        request.getPatientExamId()
                ));

        if (patientExam.getStatus() != PatientExamStatus.PENDING) {
            throw new InvalidExamRoomQueueRequestException(
                    "PENDING 상태의 검사만 대기열에 등록할 수 있습니다: "
                            + patientExam.getStatus()
            );
        }

        if (examRoomQueueRepository.existsByPatientExam_IdAndStatusIn(
                patientExam.getId(),
                ACTIVE_STATUSES
        )) {
            throw new DuplicateActiveExamRoomQueueException(
                    patientExam.getId()
            );
        }

        Long examinationRoomId = patientExam
                .getExamCatalog()
                .getExaminationRoom()
                .getId();
        ExaminationRoom examinationRoom = patientExam
                .getExamCatalog()
                .getExaminationRoom();

        if (!examinationRoom.isActive()) {
            throw new InvalidExamRoomQueueRequestException(
                    "비활성화된 검사실에는 대기열을 등록할 수 없습니다"
            );
        }

        int queueNumber = examRoomQueueRepository
                .findMaxQueueNumberByExaminationRoomId(
                        examinationRoomId
                ) + 1;

        ExamRoomQueue queue = new ExamRoomQueue(
                patientExam,
                queueNumber
        );

        patientExam.waitForExam();
        patientExamRepository.save(patientExam);
        ExamRoomQueue savedQueue = examRoomQueueRepository.saveAndFlush(queue);

        return ExamRoomQueueResponse.from(savedQueue);
    }

    private void validateRequest(ExamRoomQueueCreateRequest request) {
        if (request == null || request.getPatientExamId() == null) {
            throw new InvalidExamRoomQueueRequestException(
                    "환자 검사항목 ID가 필요합니다"
            );
        }
    }
}
