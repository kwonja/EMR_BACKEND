package com.example.demo.department.dto;

import com.example.demo.department.domain.Department;

public class DepartmentResponse {

    private final Long id;
    private final String name;
    private final String departmentType;

    public DepartmentResponse(Long id, String name, String departmentType) {
        this.id = id;
        this.name = name;
        this.departmentType = departmentType;
    }

    public static DepartmentResponse from(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getDepartmentType()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartmentType() {
        return departmentType;
    }
}
