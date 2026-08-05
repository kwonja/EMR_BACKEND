package com.example.demo.appointment.controller;

import com.example.demo.appointment.dto.AppointmentCreateRequest;
import com.example.demo.appointment.dto.AppointmentResponse;
import com.example.demo.appointment.dto.AppointmentStatusUpdateRequest;
import com.example.demo.appointment.service.AppointmentService;
import com.example.demo.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "예약")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(
            summary = "예약 등록",
            description = "환자와 예약 일시를 이용해 새로운 검진 예약을 등록합니다."
    )
    @PostMapping
    public ApiResponse<AppointmentResponse> create(
            @RequestBody AppointmentCreateRequest request
    ) {
        return ApiResponse.success(
                "예약 등록 성공",
                appointmentService.create(request)
        );
    }

    @Operation(
            summary = "예약 목록 조회",
            description = "등록된 전체 예약 목록을 조회합니다."
    )
    @GetMapping
    public ApiResponse<List<AppointmentResponse>> findAll() {
        return ApiResponse.success(
                "예약 목록 조회 성공",
                appointmentService.findAll()
        );
    }

    @Operation(
            summary = "예약 상세 조회",
            description = "예약 ID를 이용해 예약의 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public ApiResponse<AppointmentResponse> findById(@PathVariable Long id) {
        return ApiResponse.success(
                "예약 조회 성공",
                appointmentService.findById(id)
        );
    }

    @Operation(
            summary = "예약 상태 변경",
            description = "예약 ID를 이용해 예약 상태를 변경합니다."
    )
    @PatchMapping("/{id}/status")
    public ApiResponse<AppointmentResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody AppointmentStatusUpdateRequest request
    ) {
        return ApiResponse.success(
                "예약 상태 변경 성공",
                appointmentService.updateStatus(id, request)
        );
    }

    @Operation(
            summary = "예약 삭제",
            description = "예약 ID에 해당하는 예약을 삭제합니다."
    )
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return ApiResponse.success("예약 삭제 성공", null);
    }
}
