package com.example.demo.examroomqueue.controller;

import com.example.demo.examroomqueue.domain.ExamRoomQueueStatus;
import com.example.demo.examroomqueue.dto.ExamRoomQueueCreateRequest;
import com.example.demo.examroomqueue.dto.ExamRoomQueueResponse;
import com.example.demo.examroomqueue.service.ExamRoomQueueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "검사실 대기열")
public class ExamRoomQueueController {

    private final ExamRoomQueueService examRoomQueueService;

    public ExamRoomQueueController(
            ExamRoomQueueService examRoomQueueService
    ) {
        this.examRoomQueueService = examRoomQueueService;
    }

    @Operation(
            summary = "검사실 대기열 등록",
            description = "환자 검사항목을 해당 검사실 대기열에 등록하고 대기번호를 발급합니다."
    )
    @PostMapping
    public ExamRoomQueueResponse create(
            @RequestBody ExamRoomQueueCreateRequest request
    ) {
        return examRoomQueueService.create(request);
    }

    @Operation(
            summary = "대기열 목록 조회",
            description = "대기열 목록을 조회합니다. 검사실 ID와 대기 상태는 선택 조건입니다."
    )
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

    @Operation(
            summary = "검사실별 대기열 조회",
            description = "검사실 ID에 해당하는 대기열을 대기번호 순으로 조회합니다. 상태 조건은 선택 사항입니다."
    )
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

    @Operation(
            summary = "대기 환자 호출",
            description = "WAITING 상태의 대기열을 CALLED 상태로 변경하고 호출 시간을 기록합니다."
    )
    @PatchMapping("/{queueId}/call")
    public ExamRoomQueueResponse call(
            @PathVariable Long queueId
    ) {
        return examRoomQueueService.call(queueId);
    }

    @Operation(
            summary = "검사실 입장",
            description = "호출된 환자를 검사실에 입장시키고 환자 검사를 진행 중 상태로 변경합니다."
    )
    @PatchMapping("/{queueId}/enter")
    public ExamRoomQueueResponse enter(
            @PathVariable Long queueId
    ) {
        return examRoomQueueService.enter(queueId);
    }

    @Operation(
            summary = "검사 완료",
            description = "진행 중인 검사를 완료하고 대기열을 퇴실 상태로 변경합니다."
    )
    @PatchMapping("/{queueId}/complete")
    public ExamRoomQueueResponse complete(
            @PathVariable Long queueId
    ) {
        return examRoomQueueService.complete(queueId);
    }
}
