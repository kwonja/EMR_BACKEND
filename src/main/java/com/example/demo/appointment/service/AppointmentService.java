package com.example.demo.appointment.service;

import com.example.demo.appointment.domain.Appointment;
import com.example.demo.appointment.domain.AppointmentStatus;
import com.example.demo.appointment.dto.AppointmentCreateRequest;
import com.example.demo.appointment.dto.AppointmentResponse;
import com.example.demo.appointment.dto.AppointmentStatusUpdateRequest;
import com.example.demo.appointment.exception.AppointmentNotFoundException;
import com.example.demo.appointment.exception.InvalidAppointmentStaffException;
import com.example.demo.appointment.exception.InvalidAppointmentStatusException;
import com.example.demo.appointment.repository.AppointmentRepository;
import com.example.demo.department.domain.Department;
import com.example.demo.department.exception.DepartmentNotFoundException;
import com.example.demo.department.repository.DepartmentRepository;
import com.example.demo.patient.domain.Patient;
import com.example.demo.patient.exception.PatientNotFoundException;
import com.example.demo.patient.repository.PatientRepository;
import com.example.demo.staff.domain.Staff;
import com.example.demo.staff.domain.StaffType;
import com.example.demo.staff.exception.StaffNotFoundException;
import com.example.demo.staff.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;
    private final StaffRepository staffRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DepartmentRepository departmentRepository,
            StaffRepository staffRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
        this.staffRepository = staffRepository;
    }

    public AppointmentResponse create(AppointmentCreateRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(
                        request.getPatientId()
                ));

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(
                        request.getDepartmentId()
                ));

        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new StaffNotFoundException(
                        request.getStaffId()
                ));

        if (!staff.getDepartment().getId().equals(department.getId())) {
            throw new InvalidAppointmentStaffException(
                    "담당 직원이 선택한 부서에 소속되어 있지 않습니다"
            );
        }

        if (!staff.isActive()) {
            throw new InvalidAppointmentStaffException(
                    "비활성화된 직원은 예약 담당자로 지정할 수 없습니다"
            );
        }

        if (staff.getStaffType() != StaffType.DOCTOR) {
            throw new InvalidAppointmentStaffException(
                    "의사만 예약 담당자로 지정할 수 있습니다"
            );
        }

        Appointment appointment = new Appointment(
                patient,
                department,
                staff,
                request.getScheduledAt(),
                request.getSymptoms()
        );

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentResponse.from(savedAppointment);
    }

    public List<AppointmentResponse> findAll() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentResponse> responses = new ArrayList<>();

        for (Appointment appointment : appointments) {
            responses.add(AppointmentResponse.from(appointment));
        }

        return responses;
    }

    public AppointmentResponse findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        return AppointmentResponse.from(appointment);
    }

    public AppointmentResponse updateStatus(
            Long id,
            AppointmentStatusUpdateRequest request
    ) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        AppointmentStatus status;

        try {
            status = AppointmentStatus.valueOf(
                    request.getStatus().toUpperCase()
            );
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new InvalidAppointmentStatusException(request.getStatus());
        }

        appointment.updateStatus(status);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentResponse.from(savedAppointment);
    }

    public void delete(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        appointmentRepository.delete(appointment);
    }
}
