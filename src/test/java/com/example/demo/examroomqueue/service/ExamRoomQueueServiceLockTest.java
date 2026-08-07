package com.example.demo.examroomqueue.service;

import com.example.demo.examination.domain.ExaminationRoom;
import com.example.demo.examination.repository.ExaminationRoomRepository;
import com.example.demo.examroomqueue.domain.ExamRoomQueue;
import com.example.demo.examroomqueue.domain.ExamRoomQueueStatus;
import com.example.demo.examroomqueue.exception.InvalidExamRoomQueueStatusException;
import com.example.demo.examroomqueue.repository.ExamRoomQueueRepository;
import com.example.demo.patientexam.repository.PatientExamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamRoomQueueServiceLockTest {

    @Mock
    private ExamRoomQueueRepository examRoomQueueRepository;

    @Mock
    private PatientExamRepository patientExamRepository;

    @Mock
    private ExaminationRoomRepository examinationRoomRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private ExamRoomQueueService examRoomQueueService;

    @Test
    void call은_검사실을_잠근_다음_대기열을_조회한다() {
        Long queueId = 1L;
        Long examinationRoomId = 10L;
        ExaminationRoom examinationRoom = org.mockito.Mockito.mock(
                ExaminationRoom.class
        );
        ExamRoomQueue queue = org.mockito.Mockito.mock(
                ExamRoomQueue.class
        );

        when(examRoomQueueRepository.findExaminationRoomIdByQueueId(queueId))
                .thenReturn(Optional.of(examinationRoomId));
        when(examinationRoomRepository.findByIdForUpdate(examinationRoomId))
                .thenReturn(Optional.of(examinationRoom));
        when(examRoomQueueRepository.findByIdWithDetails(queueId))
                .thenReturn(Optional.of(queue));
        when(queue.getStatus()).thenReturn(ExamRoomQueueStatus.CALLED);

        assertThrows(
                InvalidExamRoomQueueStatusException.class,
                () -> examRoomQueueService.call(queueId)
        );

        InOrder inOrder = inOrder(
                examRoomQueueRepository,
                examinationRoomRepository
        );
        inOrder.verify(examRoomQueueRepository)
                .findExaminationRoomIdByQueueId(queueId);
        inOrder.verify(examinationRoomRepository)
                .findByIdForUpdate(examinationRoomId);
        inOrder.verify(examRoomQueueRepository)
                .findByIdWithDetails(queueId);
    }
}
