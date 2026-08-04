package com.example.demo.examination.controller;

import com.example.demo.examination.dto.ExaminationRoomCreateRequest;
import com.example.demo.examination.dto.ExaminationRoomResponse;
import com.example.demo.examination.service.ExaminationRoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping
    public List<ExaminationRoomResponse> findAll(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String roomName,
            @RequestParam(required = false) String roomNo
    ) {
        return examinationRoomService.findAll(
                location,
                roomName,
                roomNo
        );
    }
}
