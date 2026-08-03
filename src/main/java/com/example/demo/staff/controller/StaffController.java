package com.example.demo.staff.controller;

import com.example.demo.staff.dto.StaffCreateRequest;
import com.example.demo.staff.dto.StaffResponse;
import com.example.demo.staff.dto.StaffUpdateRequest;
import com.example.demo.staff.service.StaffService;
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
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping
    public StaffResponse create(@RequestBody StaffCreateRequest request) {

        return staffService.create(request);
    }

    @GetMapping("/{id}")
    public StaffResponse findById(@PathVariable Long id) {
        return staffService.findById(id);
    }

    @GetMapping
    public List<StaffResponse> findAll() {
        return staffService.findAll();
    }

    @PatchMapping("/{id}")
    public StaffResponse update(
            @PathVariable Long id,
            @RequestBody StaffUpdateRequest request
    ) {
        return staffService.update(id, request);
    }
}
