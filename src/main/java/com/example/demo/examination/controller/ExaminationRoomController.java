package com.example.demo.examination.controller;

import com.example.demo.examination.dto.ExaminationRoomCreateRequest;
import com.example.demo.examination.dto.ExaminationRoomResponse;
import com.example.demo.examination.service.ExaminationRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/examination-rooms")
@Tag(name = "검사실")
public class ExaminationRoomController {

    private final ExaminationRoomService examinationRoomService;

    public ExaminationRoomController(
            ExaminationRoomService examinationRoomService
    ) {
        this.examinationRoomService = examinationRoomService;
    }

    @Operation(
            summary = "검사실 등록",
            description = "검사실 이름, 번호와 위치를 이용해 새로운 검사실을 등록합니다."
    )
    @PostMapping
    public ExaminationRoomResponse create(
            @RequestBody ExaminationRoomCreateRequest request
    ) {
        return examinationRoomService.create(request);
    }

    @Operation(
            summary = "검사실 목록 조회",
            description = "검사실 목록을 조회합니다. 위치, 검사실 이름과 번호는 선택 조건입니다."
    )
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
