package com.example.demo.appointment.controller;

import com.example.demo.appointment.dto.AppointmentCreateRequest;
import com.example.demo.appointment.dto.AppointmentResponse;
import com.example.demo.appointment.dto.AppointmentStatusUpdateRequest;
import com.example.demo.appointment.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public AppointmentResponse create(
            @RequestBody AppointmentCreateRequest request
    ) {
        return appointmentService.create(request);
    }

    @GetMapping
    public List<AppointmentResponse> findAll() {
        return appointmentService.findAll();
    }

    @GetMapping("/{id}")
    public AppointmentResponse findById(@PathVariable Long id) {
        return appointmentService.findById(id);
    }

    @PatchMapping("/{id}/status")
    public AppointmentResponse updateStatus(
            @PathVariable Long id,
            @RequestBody AppointmentStatusUpdateRequest request
    ) {
        return appointmentService.updateStatus(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        appointmentService.delete(id);
    }
}
