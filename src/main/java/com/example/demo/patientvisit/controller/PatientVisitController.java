package com.example.demo.patientvisit.controller;

import com.example.demo.patientvisit.dto.PatientVisitCreateRequest;
import com.example.demo.patientvisit.dto.PatientVisitProgressResponse;
import com.example.demo.patientvisit.dto.PatientVisitResponse;
import com.example.demo.patientvisit.service.PatientVisitService;
import com.example.demo.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patient-visits")
@Tag(name = "환자 방문")
public class PatientVisitController {

    private final PatientVisitService patientVisitService;

    public PatientVisitController(PatientVisitService patientVisitService) {
        this.patientVisitService = patientVisitService;
    }

    @Operation(
            summary = "환자 방문 접수",
            description = "환자 ID와 선택적인 예약 ID를 이용해 환자 방문을 접수합니다."
    )
    @PostMapping
    public ApiResponse<PatientVisitResponse> create(
            @RequestBody PatientVisitCreateRequest request
    ) {
        return ApiResponse.success(
                "환자 방문 접수 성공",
                patientVisitService.create(request)
        );
    }

    @Operation(
            summary = "환자 방문 상세 조회",
            description = "환자 방문 ID를 이용해 방문 정보를 조회합니다."
    )
    @GetMapping("/{patientVisitId}")
    public ApiResponse<PatientVisitResponse> findById(
            @PathVariable Long patientVisitId
    ) {
        return ApiResponse.success(
                "환자 방문 조회 성공",
                patientVisitService.findById(patientVisitId)
        );
    }

    @Operation(
            summary = "환자 검진 진행 현황 조회",
            description = "환자 방문의 전체 검사, 완료 및 남은 검사 개수와 현재 검사를 조회합니다."
    )
    @GetMapping("/{patientVisitId}/progress")
    public ApiResponse<PatientVisitProgressResponse> findProgress(
            @PathVariable Long patientVisitId
    ) {
        return ApiResponse.success(
                "환자 검진 진행 현황 조회 성공",
                patientVisitService.findProgress(patientVisitId)
        );
    }

    @Operation(
            summary = "환자 방문 완료",
            description = "모든 검사가 완료된 환자의 방문을 완료하고 퇴실 시간을 기록합니다."
    )
    @PatchMapping("/{patientVisitId}/complete")
    public ApiResponse<PatientVisitResponse> complete(
            @PathVariable Long patientVisitId
    ) {
        return ApiResponse.success(
                "환자 방문 완료 처리 성공",
                patientVisitService.complete(patientVisitId)
        );
    }
}
