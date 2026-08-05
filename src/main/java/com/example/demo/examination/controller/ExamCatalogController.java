package com.example.demo.examination.controller;

import com.example.demo.examination.dto.ExamCatalogCreateRequest;
import com.example.demo.examination.dto.ExamCatalogResponse;
import com.example.demo.examination.service.ExamCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exam-catalog")
@Tag(name = "검사항목")
public class ExamCatalogController {

    private final ExamCatalogService examCatalogService;

    public ExamCatalogController(ExamCatalogService examCatalogService) {
        this.examCatalogService = examCatalogService;
    }

    @Operation(
            summary = "검사항목 등록",
            description = "검사실에 새로운 검사항목을 등록합니다."
    )
    @PostMapping
    public ExamCatalogResponse create(
            @RequestBody ExamCatalogCreateRequest request
    ) {
        return examCatalogService.create(request);
    }

    @Operation(
            summary = "검사항목 목록 조회",
            description = "검사실 정보를 포함한 전체 검사항목 목록을 조회합니다."
    )
    @GetMapping
    public List<ExamCatalogResponse> findAll() {
        return examCatalogService.findAll();
    }

    @Operation(
            summary = "검사항목 상세 조회",
            description = "검사항목 ID를 이용해 검사실 정보를 포함한 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public ExamCatalogResponse findById(@PathVariable Long id) {
        return examCatalogService.findById(id);
    }
}
