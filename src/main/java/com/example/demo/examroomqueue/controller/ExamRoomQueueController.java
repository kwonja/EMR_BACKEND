package com.example.demo.examroomqueue.controller;

import com.example.demo.examroomqueue.domain.ExamRoomQueueStatus;
import com.example.demo.examroomqueue.dto.ExamRoomQueueCreateRequest;
import com.example.demo.examroomqueue.dto.ExamRoomQueueResponse;
import com.example.demo.examroomqueue.service.ExamRoomQueueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exam-room-queues")
public class ExamRoomQueueController {

    private final ExamRoomQueueService examRoomQueueService;

    public ExamRoomQueueController(
            ExamRoomQueueService examRoomQueueService
    ) {
        this.examRoomQueueService = examRoomQueueService;
    }

    @PostMapping
    public ExamRoomQueueResponse create(
            @RequestBody ExamRoomQueueCreateRequest request
    ) {
        return examRoomQueueService.create(request);
    }

    @GetMapping
    public List<ExamRoomQueueResponse> findAll(
            @RequestParam(required = false) Long examinationRoomId,
            @RequestParam(required = false) ExamRoomQueueStatus status
    ) {
        return examRoomQueueService.findAll(
                examinationRoomId,
                status
        );
    }

    @GetMapping("/examination-rooms/{examinationRoomId}")
    public List<ExamRoomQueueResponse> findAllByExaminationRoomId(
            @PathVariable Long examinationRoomId,
            @RequestParam(required = false) ExamRoomQueueStatus status
    ) {
        return examRoomQueueService.findAll(
                examinationRoomId,
                status
        );
    }

    @PatchMapping("/{queueId}/call")
    public ExamRoomQueueResponse call(
            @PathVariable Long queueId
    ) {
        return examRoomQueueService.call(queueId);
    }

    @PatchMapping("/{queueId}/enter")
    public ExamRoomQueueResponse enter(
            @PathVariable Long queueId
    ) {
        return examRoomQueueService.enter(queueId);
    }

    @PatchMapping("/{queueId}/complete")
    public ExamRoomQueueResponse complete(
            @PathVariable Long queueId
    ) {
        return examRoomQueueService.complete(queueId);
    }
}
