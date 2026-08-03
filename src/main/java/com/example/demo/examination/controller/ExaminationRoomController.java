package com.example.demo.examination.controller;

import com.example.demo.examination.dto.ExaminationRoomCreateRequest;
import com.example.demo.examination.dto.ExaminationRoomResponse;
import com.example.demo.examination.service.ExaminationRoomService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/examination-rooms")
public class ExaminationRoomController {

    private final ExaminationRoomService examinationRoomService;

    public ExaminationRoomController(
            ExaminationRoomService examinationRoomService
    ) {
        this.examinationRoomService = examinationRoomService;
    }

    @PostMapping
    public ExaminationRoomResponse create(
            @RequestBody ExaminationRoomCreateRequest request
    ) {
        return examinationRoomService.create(request);
    }
}
