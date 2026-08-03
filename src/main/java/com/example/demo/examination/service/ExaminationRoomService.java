package com.example.demo.examination.service;

import com.example.demo.department.domain.Department;
import com.example.demo.department.exception.DepartmentNotFoundException;
import com.example.demo.department.repository.DepartmentRepository;
import com.example.demo.examination.domain.ExaminationRoom;
import com.example.demo.examination.dto.ExaminationRoomCreateRequest;
import com.example.demo.examination.dto.ExaminationRoomResponse;
import com.example.demo.examination.repository.ExaminationRoomRepository;
import org.springframework.stereotype.Service;

@Service
public class ExaminationRoomService {

    private final ExaminationRoomRepository examinationRoomRepository;
    private final DepartmentRepository departmentRepository;

    public ExaminationRoomService(
            ExaminationRoomRepository examinationRoomRepository,
            DepartmentRepository departmentRepository
    ) {
        this.examinationRoomRepository = examinationRoomRepository;
        this.departmentRepository = departmentRepository;
    }

    public ExaminationRoomResponse create(ExaminationRoomCreateRequest request) {
        Department department = null;

        if (request.getDepartmentId() != null) {
            department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new DepartmentNotFoundException(
                            request.getDepartmentId()
                    ));
        }

        ExaminationRoom examinationRoom = new ExaminationRoom(
                department,
                request.getName(),
                request.getLocation(),
                request.getDescription()
        );

        ExaminationRoom savedExaminationRoom =
                examinationRoomRepository.save(examinationRoom);

        return ExaminationRoomResponse.from(savedExaminationRoom);
    }
}
