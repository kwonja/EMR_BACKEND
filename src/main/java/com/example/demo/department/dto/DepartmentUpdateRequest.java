package com.example.demo.department.dto;

public class DepartmentUpdateRequest {

    private String name;
    private String departmentType;

    public DepartmentUpdateRequest() {
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
