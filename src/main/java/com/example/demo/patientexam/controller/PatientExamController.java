package com.example.demo.patientexam.controller;

import com.example.demo.patientexam.dto.PatientExamCreateRequest;
import com.example.demo.patientexam.dto.PatientExamResponse;
import com.example.demo.patientexam.service.PatientExamService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patient-visits/{patientVisitId}/exams")
public class PatientExamController {

    private final PatientExamService patientExamService;

    public PatientExamController(PatientExamService patientExamService) {
        this.patientExamService = patientExamService;
    }

    @PostMapping
    public List<PatientExamResponse> create(
            @PathVariable Long patientVisitId,
            @RequestBody PatientExamCreateRequest request
    ) {
        return patientExamService.create(patientVisitId, request);
    }
}
