package com.example.demo.common.exception;

import com.example.demo.appointment.exception.AppointmentNotFoundException;
import com.example.demo.appointment.exception.InvalidAppointmentStatusException;
import com.example.demo.department.exception.DepartmentNotFoundException;
import com.example.demo.examination.exception.DuplicateExamCodeException;
import com.example.demo.examination.exception.ExaminationRoomNotFoundException;
import com.example.demo.patient.exception.DuplicatePatientNumberException;
import com.example.demo.patient.exception.PatientNotFoundException;
import com.example.demo.patientvisit.exception.InvalidPatientVisitAppointmentException;
import com.example.demo.staff.exception.InvalidStaffTypeException;
import com.example.demo.staff.exception.StaffNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatePatientNumberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePatientNumber(
            DuplicatePatientNumberException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "DUPLICATE_PATIENT_NUMBER",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientNotFound(
            PatientNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "PATIENT_NOT_FOUND",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDepartmentNotFound(
            DepartmentNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "DEPARTMENT_NOT_FOUND",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(InvalidStaffTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStaffType(
            InvalidStaffTypeException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "INVALID_STAFF_TYPE",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(StaffNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStaffNotFound(
            StaffNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "STAFF_NOT_FOUND",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentNotFound(
            AppointmentNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "APPOINTMENT_NOT_FOUND",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(InvalidAppointmentStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAppointmentStatus(
            InvalidAppointmentStatusException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "INVALID_APPOINTMENT_STATUS",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(ExaminationRoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExaminationRoomNotFound(
            ExaminationRoomNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "EXAMINATION_ROOM_NOT_FOUND",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(DuplicateExamCodeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateExamCode(
            DuplicateExamCodeException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "DUPLICATE_EXAM_CODE",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(InvalidPatientVisitAppointmentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPatientVisitAppointment(
            InvalidPatientVisitAppointmentException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "INVALID_PATIENT_VISIT_APPOINTMENT",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}
