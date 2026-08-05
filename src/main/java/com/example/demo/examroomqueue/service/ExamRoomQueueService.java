package com.example.demo.examroomqueue.service;

import com.example.demo.examroomqueue.domain.ExamRoomQueue;
import com.example.demo.examroomqueue.domain.ExamRoomQueueStatus;
import com.example.demo.examroomqueue.dto.ExamRoomQueueCreateRequest;
import com.example.demo.examroomqueue.dto.ExamRoomQueueResponse;
import com.example.demo.examroomqueue.exception.CalledQueueAlreadyExistsException;
import com.example.demo.examroomqueue.exception.DuplicateActiveExamRoomQueueException;
import com.example.demo.examroomqueue.exception.ExamRoomQueueNotFoundException;
import com.example.demo.examroomqueue.exception.InvalidExamRoomQueueRequestException;
import com.example.demo.examroomqueue.exception.InvalidExamRoomQueueStatusException;
import com.example.demo.examroomqueue.exception.OutOfOrderPatientExamQueueException;
import com.example.demo.examroomqueue.exception.PatientExamNotFoundException;
import com.example.demo.examroomqueue.repository.ExamRoomQueueRepository;
import com.example.demo.examination.domain.ExaminationRoom;
import com.example.demo.patientexam.domain.PatientExam;
import com.example.demo.patientexam.domain.PatientExamStatus;
import com.example.demo.patientexam.repository.PatientExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        Integer firstPendingSequenceNumber = patientExamRepository
                .findFirstSequenceNumberByStatus(
                        patientExam.getPatientVisit().getId(),
                        PatientExamStatus.PENDING
                );

        if (firstPendingSequenceNumber != null
                && patientExam.getSequenceNumber()
                != firstPendingSequenceNumber) {
            throw new OutOfOrderPatientExamQueueException(
                    firstPendingSequenceNumber,
                    patientExam.getSequenceNumber()
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

        ExamRoomQueue savedQueue = createQueue(patientExam);

        return ExamRoomQueueResponse.from(savedQueue);
    }

    public List<ExamRoomQueueResponse> findAll(
            Long examinationRoomId,
            ExamRoomQueueStatus status
    ) {
        List<ExamRoomQueue> queues = examRoomQueueRepository
                .findAllWithDetails(
                        examinationRoomId,
                        status
                );

        List<ExamRoomQueueResponse> responses = new ArrayList<>();

        for (ExamRoomQueue queue : queues) {
            responses.add(ExamRoomQueueResponse.from(queue));
        }

        return responses;
    }

    @Transactional
    public ExamRoomQueueResponse call(Long queueId) {
        ExamRoomQueue queue = examRoomQueueRepository
                .findByIdWithDetails(queueId)
                .orElseThrow(() -> new ExamRoomQueueNotFoundException(
                        queueId
                ));

        if (queue.getStatus() != ExamRoomQueueStatus.WAITING) {
            throw new InvalidExamRoomQueueStatusException(
                    "WAITING 상태의 대기열만 호출할 수 있습니다: "
                            + queue.getStatus()
            );
        }

        Long examinationRoomId = queue
                .getPatientExam()
                .getExamCatalog()
                .getExaminationRoom()
                .getId();

        if (examRoomQueueRepository.existsByExaminationRoomIdAndStatus(
                examinationRoomId,
                ExamRoomQueueStatus.CALLED
        )) {
            throw new CalledQueueAlreadyExistsException(
                    examinationRoomId
            );
        }

        queue.call();
        ExamRoomQueue savedQueue = examRoomQueueRepository
                .saveAndFlush(queue);

        return ExamRoomQueueResponse.from(savedQueue);
    }

    @Transactional
    public ExamRoomQueueResponse enter(Long queueId) {
        ExamRoomQueue queue = examRoomQueueRepository
                .findByIdWithDetails(queueId)
                .orElseThrow(() -> new ExamRoomQueueNotFoundException(
                        queueId
                ));

        if (queue.getStatus() != ExamRoomQueueStatus.CALLED) {
            throw new InvalidExamRoomQueueStatusException(
                    "CALLED 상태의 대기열만 입장할 수 있습니다: "
                            + queue.getStatus()
            );
        }

        PatientExam patientExam = queue.getPatientExam();

        if (patientExam.getStatus() != PatientExamStatus.WAITING) {
            throw new InvalidExamRoomQueueStatusException(
                    "WAITING 상태의 환자 검사만 시작할 수 있습니다: "
                            + patientExam.getStatus()
            );
        }

        queue.enter();
        patientExam.start();
        patientExamRepository.save(patientExam);
        ExamRoomQueue savedQueue = examRoomQueueRepository
                .saveAndFlush(queue);

        return ExamRoomQueueResponse.from(savedQueue);
    }

    @Transactional
    public ExamRoomQueueResponse complete(Long queueId) {
        ExamRoomQueue queue = examRoomQueueRepository
                .findByIdWithDetails(queueId)
                .orElseThrow(() -> new ExamRoomQueueNotFoundException(
                        queueId
                ));

        if (queue.getStatus() != ExamRoomQueueStatus.ENTERED) {
            throw new InvalidExamRoomQueueStatusException(
                    "ENTERED 상태의 대기열만 검사를 완료할 수 있습니다: "
                            + queue.getStatus()
            );
        }

        PatientExam patientExam = queue.getPatientExam();

        if (patientExam.getStatus() != PatientExamStatus.IN_PROGRESS) {
            throw new InvalidExamRoomQueueStatusException(
                    "IN_PROGRESS 상태의 환자 검사만 완료할 수 있습니다: "
                            + patientExam.getStatus()
            );
        }

        queue.exit();
        patientExam.complete();
        patientExamRepository.save(patientExam);
        ExamRoomQueue savedQueue = examRoomQueueRepository
                .saveAndFlush(queue);

        return ExamRoomQueueResponse.from(savedQueue);
    }

    private ExamRoomQueue createQueue(PatientExam patientExam) {
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
                        examinationRoom.getId()
                ) + 1;

        ExamRoomQueue queue = new ExamRoomQueue(
                patientExam,
                queueNumber
        );

        patientExam.waitForExam();
        patientExamRepository.save(patientExam);

        return examRoomQueueRepository.saveAndFlush(queue);
    }

    private void validateRequest(ExamRoomQueueCreateRequest request) {
        if (request == null || request.getPatientExamId() == null) {
            throw new InvalidExamRoomQueueRequestException(
                    "환자 검사항목 ID가 필요합니다"
            );
        }
    }

}
