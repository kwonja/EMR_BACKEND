package com.example.demo.department.controller;

import com.example.demo.department.dto.DepartmentCreateRequest;
import com.example.demo.department.dto.DepartmentResponse;
import com.example.demo.department.dto.DepartmentUpdateRequest;
import com.example.demo.department.service.DepartmentService;
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
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {

        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentResponse create(
            @RequestBody DepartmentCreateRequest request
    ) {
        return departmentService.create(request);
    }

    @GetMapping("/{id}")
    public DepartmentResponse findById(@PathVariable Long id) {
        return departmentService.findById(id);
    }

    @GetMapping
    public List<DepartmentResponse> findAll() {
        return departmentService.findAll();
    }

    @PatchMapping("/{id}")
    public DepartmentResponse update(
            @PathVariable Long id,
            @RequestBody DepartmentUpdateRequest request
    ) {
        return departmentService.update(id, request);
    }
}
