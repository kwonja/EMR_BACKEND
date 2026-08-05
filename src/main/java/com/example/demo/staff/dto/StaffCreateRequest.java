package com.example.demo.staff.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class StaffCreateRequest {

    @Schema(description = "부서 ID", example = "1")
    private Long departmentId;

    @Schema(description = "직원 이름", example = "홍길동")
    private String name;

    @Schema(
            description = "직원 유형",
            example = "DOCTOR",
            allowableValues = {"DOCTOR", "NURSE", "ADMIN"}
    )
    private String staffType;

    public StaffCreateRequest() {
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }
}
