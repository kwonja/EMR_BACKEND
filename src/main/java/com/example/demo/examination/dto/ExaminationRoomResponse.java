package com.example.demo.examination.dto;

import com.example.demo.department.domain.Department;
import com.example.demo.examination.domain.ExaminationRoom;

public class ExaminationRoomResponse {

    private final Long id;
    private final Long departmentId;
    private final String departmentName;
    private final String name;
    private final String location;
    private final String description;
    private final boolean active;

    public ExaminationRoomResponse(
            Long id,
            Long departmentId,
            String departmentName,
            String name,
            String location,
            String description,
            boolean active
    ) {
        this.id = id;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.name = name;
        this.location = location;
        this.description = description;
        this.active = active;
    }

    public static ExaminationRoomResponse from(ExaminationRoom examinationRoom) {
        Department department = examinationRoom.getDepartment();

        return new ExaminationRoomResponse(
                examinationRoom.getId(),
                department == null ? null : department.getId(),
                department == null ? null : department.getName(),
                examinationRoom.getName(),
                examinationRoom.getLocation(),
                examinationRoom.getDescription(),
                examinationRoom.isActive()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }
}
