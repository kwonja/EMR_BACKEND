package com.example.demo.patient.controller;

import com.example.demo.patient.dto.PatientCreateRequest;
import com.example.demo.patient.dto.PatientResponse;
import com.example.demo.patient.dto.PatientUpdateRequest;
import com.example.demo.patient.service.PatientService;
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
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public PatientResponse create(
            @RequestBody PatientCreateRequest request
    ) {
        return patientService.create(request);
    }

    @GetMapping("/{id}")
    public PatientResponse findById(@PathVariable Long id) {
        return patientService.findById(id);
    }

    @GetMapping
    public List<PatientResponse> findAll() {
        return patientService.findAll();
    }

    @PatchMapping("/{id}")
    public PatientResponse update(
            @PathVariable Long id,
            @RequestBody PatientUpdateRequest request
    ) {
        return patientService.update(id, request);
    }
}
