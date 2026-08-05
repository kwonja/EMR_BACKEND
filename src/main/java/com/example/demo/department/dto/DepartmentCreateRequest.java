package com.example.demo.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class DepartmentCreateRequest {

    @Schema(description = "부서 이름", example = "검진부")
    private String name;

    @Schema(description = "부서 유형", example = "EXAMINATION")
    private String departmentType;

    public DepartmentCreateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }
}
