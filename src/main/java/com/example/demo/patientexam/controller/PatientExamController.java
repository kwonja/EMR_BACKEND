package com.example.demo.patientexam.controller;

import com.example.demo.patientexam.domain.PatientExamStatus;
import com.example.demo.patientexam.dto.PatientExamCreateRequest;
import com.example.demo.patientexam.dto.PatientExamResponse;
import com.example.demo.patientexam.service.PatientExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patient-visits/{patientVisitId}/exams")
@Tag(name = "환자 검사항목")
public class PatientExamController {

    private final PatientExamService patientExamService;

    public PatientExamController(PatientExamService patientExamService) {
        this.patientExamService = patientExamService;
    }

    @Operation(
            summary = "환자 검사항목 등록",
            description = "방문한 환자가 받아야 할 검사항목과 검사 순서를 등록합니다."
    )
    @PostMapping
    public List<PatientExamResponse> create(
            @PathVariable Long patientVisitId,
            @RequestBody PatientExamCreateRequest request
    ) {
        return patientExamService.create(patientVisitId, request);
    }

    @Operation(
            summary = "환자 검사항목 목록 조회",
            description = "방문 ID에 해당하는 검사항목을 검사 순서대로 조회합니다. 상태 조건은 선택 사항입니다."
    )
    @GetMapping
    public List<PatientExamResponse> findAll(
            @PathVariable Long patientVisitId,
            @RequestParam(required = false) PatientExamStatus status
    ) {
        return patientExamService.findAll(
                patientVisitId,
                status
        );
    }
}
