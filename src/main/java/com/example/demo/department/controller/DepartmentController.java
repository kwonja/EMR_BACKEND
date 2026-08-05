package com.example.demo.department.controller;

import com.example.demo.department.dto.DepartmentCreateRequest;
import com.example.demo.department.dto.DepartmentResponse;
import com.example.demo.department.dto.DepartmentUpdateRequest;
import com.example.demo.department.service.DepartmentService;
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

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "부서")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {

        this.departmentService = departmentService;
    }

    @Operation(
            summary = "부서 등록",
            description = "부서 이름과 부서 유형을 이용해 새로운 부서를 등록합니다."
    )
    @PostMapping
    public ApiResponse<DepartmentResponse> create(
            @RequestBody DepartmentCreateRequest request
    ) {
        return ApiResponse.success(
                "부서 등록 성공",
                departmentService.create(request)
        );
    }

    @Operation(
            summary = "부서 상세 조회",
            description = "부서 ID를 이용해 부서의 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public ApiResponse<DepartmentResponse> findById(@PathVariable Long id) {
        return ApiResponse.success(
                "부서 조회 성공",
                departmentService.findById(id)
        );
    }

    @Operation(
            summary = "부서 목록 조회",
            description = "등록된 전체 부서 목록을 조회합니다."
    )
    @GetMapping
    public ApiResponse<List<DepartmentResponse>> findAll() {
        return ApiResponse.success(
                "부서 목록 조회 성공",
                departmentService.findAll()
        );
    }

    @Operation(
            summary = "부서 정보 수정",
            description = "부서 ID를 이용해 부서 이름 또는 부서 유형을 수정합니다. "
                    + "모든 항목은 선택 사항이며, 요청에 포함된 값만 변경됩니다."
    )
    @PatchMapping("/{id}")
    public ApiResponse<DepartmentResponse> update(
            @PathVariable Long id,
            @RequestBody DepartmentUpdateRequest request
    ) {
        return ApiResponse.success(
                "부서 정보 수정 성공",
                departmentService.update(id, request)
        );
    }
}
