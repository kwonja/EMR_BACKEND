package com.example.demo.department.service;

import com.example.demo.department.domain.Department;
import com.example.demo.department.dto.DepartmentCreateRequest;
import com.example.demo.department.dto.DepartmentResponse;
import com.example.demo.department.dto.DepartmentUpdateRequest;
import com.example.demo.department.exception.DepartmentNotFoundException;
import com.example.demo.department.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse create(DepartmentCreateRequest request) {
        Department department = new Department(
                request.getName(),
                request.getDepartmentType()
        );

        Department savedDepartment = departmentRepository.save(department);
        return DepartmentResponse.from(savedDepartment);
    }

    public DepartmentResponse findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        return DepartmentResponse.from(department);
    }

    public List<DepartmentResponse> findAll() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse> responses = new ArrayList<>();

        for (Department department : departments) {
            responses.add(DepartmentResponse.from(department));
        }

        return responses;
    }

    public DepartmentResponse update(
            Long id,
            DepartmentUpdateRequest request
    ) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        department.update(
                request.getName(),
                request.getDepartmentType()
        );

        Department savedDepartment = departmentRepository.save(department);
        return DepartmentResponse.from(savedDepartment);
    }
}
