package com.example.demo.examroomqueue.controller;

import com.example.demo.examroomqueue.dto.ExamRoomQueueCreateRequest;
import com.example.demo.examroomqueue.dto.ExamRoomQueueResponse;
import com.example.demo.examroomqueue.service.ExamRoomQueueService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
