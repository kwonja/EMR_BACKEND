package com.example.demo.staff.service;

import com.example.demo.department.domain.Department;
import com.example.demo.department.exception.DepartmentNotFoundException;
import com.example.demo.department.repository.DepartmentRepository;
import com.example.demo.staff.domain.Staff;
import com.example.demo.staff.domain.StaffType;
import com.example.demo.staff.dto.StaffCreateRequest;
import com.example.demo.staff.dto.StaffResponse;
import com.example.demo.staff.dto.StaffUpdateRequest;
import com.example.demo.staff.exception.InvalidStaffTypeException;
import com.example.demo.staff.exception.StaffNotFoundException;
import com.example.demo.staff.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final DepartmentRepository departmentRepository;

    public StaffService(
            StaffRepository staffRepository,
            DepartmentRepository departmentRepository
    ) {
        this.staffRepository = staffRepository;
        this.departmentRepository = departmentRepository;
    }

    public StaffResponse create(StaffCreateRequest request) {
        StaffType staffType = convertStaffType(request.getStaffType());

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(
                        request.getDepartmentId()
                ));

        Staff staff = new Staff(
                department,
                request.getName(),
                staffType
        );

        Staff savedStaff = staffRepository.save(staff);
        return StaffResponse.from(savedStaff);
    }

    public StaffResponse findById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException(id));

        return StaffResponse.from(staff);
    }

    public List<StaffResponse> findAll() {
        List<Staff> staffList = staffRepository.findAll();
        List<StaffResponse> responses = new ArrayList<>();

        for (Staff staff : staffList) {
            responses.add(StaffResponse.from(staff));
        }

        return responses;
    }

    public StaffResponse update(Long id, StaffUpdateRequest request) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException(id));

        Department department = null;
        if (request.getDepartmentId() != null) {
            department = departmentRepository
                    .findById(request.getDepartmentId())
                    .orElseThrow(() -> new DepartmentNotFoundException(
                            request.getDepartmentId()
                    ));
        }

        StaffType staffType = null;
        if (request.getStaffType() != null) {
            staffType = convertStaffType(request.getStaffType());
        }

        staff.update(
                department,
                request.getName(),
                staffType,
                request.getActive()
        );

        Staff savedStaff = staffRepository.save(staff);
        return StaffResponse.from(savedStaff);
    }

    private StaffType convertStaffType(String staffType) {
        try {
            return StaffType.valueOf(staffType.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new InvalidStaffTypeException(staffType);
        }
    }
}
