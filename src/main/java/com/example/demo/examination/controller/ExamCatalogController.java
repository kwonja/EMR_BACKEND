package com.example.demo.examination.controller;

import com.example.demo.examination.dto.ExamCatalogCreateRequest;
import com.example.demo.examination.dto.ExamCatalogResponse;
import com.example.demo.examination.service.ExamCatalogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exam-catalog")
public class ExamCatalogController {

    private final ExamCatalogService examCatalogService;

    public ExamCatalogController(ExamCatalogService examCatalogService) {
        this.examCatalogService = examCatalogService;
    }

    @PostMapping
    public ExamCatalogResponse create(
            @RequestBody ExamCatalogCreateRequest request
    ) {
        return examCatalogService.create(request);
    }

    @GetMapping
    public List<ExamCatalogResponse> findAll() {
        return examCatalogService.findAll();
    }

    @GetMapping("/{id}")
    public ExamCatalogResponse findById(@PathVariable Long id) {
        return examCatalogService.findById(id);
    }
}
