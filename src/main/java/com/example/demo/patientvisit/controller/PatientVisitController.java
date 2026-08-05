package com.example.demo.patientvisit.controller;

import com.example.demo.patientvisit.dto.PatientVisitCreateRequest;
import com.example.demo.patientvisit.dto.PatientVisitProgressResponse;
import com.example.demo.patientvisit.dto.PatientVisitResponse;
import com.example.demo.patientvisit.service.PatientVisitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patient-visits")
public class PatientVisitController {

    private final PatientVisitService patientVisitService;

    public PatientVisitController(PatientVisitService patientVisitService) {
        this.patientVisitService = patientVisitService;
    }

    @PostMapping
    public PatientVisitResponse create(
            @RequestBody PatientVisitCreateRequest request
    ) {
        return patientVisitService.create(request);
    }

    @GetMapping("/{patientVisitId}")
    public PatientVisitResponse findById(
            @PathVariable Long patientVisitId
    ) {
        return patientVisitService.findById(patientVisitId);
    }

    @GetMapping("/{patientVisitId}/progress")
    public PatientVisitProgressResponse findProgress(
            @PathVariable Long patientVisitId
    ) {
        return patientVisitService.findProgress(patientVisitId);
    }

    @PatchMapping("/{patientVisitId}/complete")
    public PatientVisitResponse complete(
            @PathVariable Long patientVisitId
    ) {
        return patientVisitService.complete(patientVisitId);
    }
}
