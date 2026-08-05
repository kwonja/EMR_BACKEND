package com.example.demo.staff.controller;

import com.example.demo.staff.dto.StaffCreateRequest;
import com.example.demo.staff.dto.StaffResponse;
import com.example.demo.staff.dto.StaffUpdateRequest;
import com.example.demo.staff.service.StaffService;
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
@RequestMapping("/api/staff")
@Tag(name = "직원")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @Operation(
            summary = "직원 등록",
            description = "부서 ID와 직원 유형을 이용해 새로운 직원을 등록합니다."
    )
    @PostMapping
    public StaffResponse create(@RequestBody StaffCreateRequest request) {

        return staffService.create(request);
    }

    @Operation(
            summary = "직원 상세 조회",
            description = "직원 ID를 이용해 직원의 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public StaffResponse findById(@PathVariable Long id) {
        return staffService.findById(id);
    }

    @Operation(
            summary = "직원 목록 조회",
            description = "등록된 전체 직원 목록을 조회합니다."
    )
    @GetMapping
    public List<StaffResponse> findAll() {
        return staffService.findAll();
    }

    @Operation(
            summary = "직원 정보 수정",
            description = "직원 ID를 이용해 직원의 이름, 유형 또는 소속 부서를 수정합니다. "
                    + "모든 항목은 선택 사항이며, 요청에 포함된 값만 변경됩니다."
    )
    @PatchMapping("/{id}")
    public StaffResponse update(
            @PathVariable Long id,
            @RequestBody StaffUpdateRequest request
    ) {
        return staffService.update(id, request);
    }
}
