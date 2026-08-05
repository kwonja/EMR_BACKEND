package com.example.demo.patient.controller;

import com.example.demo.patient.dto.PatientCreateRequest;
import com.example.demo.patient.dto.PatientResponse;
import com.example.demo.patient.dto.PatientUpdateRequest;
import com.example.demo.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@Tag(name = "환자")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(
            summary = "환자 등록",
            description = "환자의 기본정보를 이용해 새로운 환자를 등록합니다."
    )
    @PostMapping
    public PatientResponse create(
            @RequestBody PatientCreateRequest request
    ) {
        return patientService.create(request);
    }

    @Operation(
            summary = "환자 상세 조회",
            description = "환자 ID를 이용해 환자의 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public PatientResponse findById(@PathVariable Long id) {
        return patientService.findById(id);
    }

    @Operation(
            summary = "환자 목록 조회",
            description = "등록된 전체 환자 목록을 조회합니다."
    )
    @GetMapping
    public List<PatientResponse> findAll() {
        return patientService.findAll();
    }

    @Operation(
            summary = "환자 정보 수정",
            description = "환자 ID를 이용해 환자 정보를 수정합니다. "
                    + "모든 항목은 선택 사항이며, 요청에 포함된 값만 변경됩니다."
    )
    @PatchMapping("/{id}")
    public PatientResponse update(
            @PathVariable Long id,
            @RequestBody PatientUpdateRequest request
    ) {
        return patientService.update(id, request);
    }
}
