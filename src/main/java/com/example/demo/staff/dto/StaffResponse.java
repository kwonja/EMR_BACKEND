package com.example.demo.staff.dto;

import com.example.demo.staff.domain.Staff;
import com.example.demo.staff.domain.StaffType;

public class StaffResponse {

    private final Long id;
    private final Long departmentId;
    private final String departmentName;
    private final String name;
    private final StaffType staffType;
    private final boolean active;

    public StaffResponse(
            Long id,
            Long departmentId,
            String departmentName,
            String name,
            StaffType staffType,
            boolean active
    ) {
        this.id = id;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.name = name;
        this.staffType = staffType;
        this.active = active;
    }

    public static StaffResponse from(Staff staff) {
        return new StaffResponse(
                staff.getId(),
                staff.getDepartment().getId(),
                staff.getDepartment().getName(),
                staff.getName(),
                staff.getStaffType(),
                staff.isActive()
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

    public StaffType getStaffType() {
        return staffType;
    }

    public boolean isActive() {
        return active;
    }
}
