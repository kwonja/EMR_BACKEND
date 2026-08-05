package com.example.demo.common.exception;

import com.example.demo.appointment.exception.AppointmentNotFoundException;
import com.example.demo.appointment.exception.InvalidAppointmentStatusException;
import com.example.demo.department.exception.DepartmentNotFoundException;
import com.example.demo.examination.exception.DuplicateExamCodeException;
import com.example.demo.examination.exception.ExamCatalogNotFoundException;
import com.example.demo.examination.exception.ExaminationRoomNotFoundException;
import com.example.demo.examroomqueue.exception.CalledQueueAlreadyExistsException;
import com.example.demo.examroomqueue.exception.DuplicateActiveExamRoomQueueException;
import com.example.demo.examroomqueue.exception.ExamRoomQueueNotFoundException;
import com.example.demo.examroomqueue.exception.InvalidExamRoomQueueRequestException;
import com.example.demo.examroomqueue.exception.InvalidExamRoomQueueStatusException;
import com.example.demo.examroomqueue.exception.OutOfOrderPatientExamQueueException;
import com.example.demo.examroomqueue.exception.PatientExamNotFoundException;
import com.example.demo.patient.exception.DuplicatePatientNumberException;
import com.example.demo.patient.exception.PatientNotFoundException;
import com.example.demo.patientexam.exception.DuplicatePatientExamSequenceException;
import com.example.demo.patientexam.exception.InvalidPatientExamRequestException;
import com.example.demo.patientexam.exception.PatientVisitNotFoundException;
import com.example.demo.patientvisit.exception.InvalidPatientVisitAppointmentException;
import com.example.demo.staff.exception.InvalidStaffTypeException;
import com.example.demo.staff.exception.StaffNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(PatientVisitNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientVisitNotFound(
            PatientVisitNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "PATIENT_VISIT_NOT_FOUND",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(ExamCatalogNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExamCatalogNotFound(
            ExamCatalogNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "EXAM_CATALOG_NOT_FOUND",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(InvalidPatientExamRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPatientExamRequest(
            InvalidPatientExamRequestException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "INVALID_PATIENT_EXAM_REQUEST",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(DuplicatePatientExamSequenceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePatientExamSequence(
            DuplicatePatientExamSequenceException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "DUPLICATE_PATIENT_EXAM_SEQUENCE",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(PatientExamNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientExamNotFound(
            PatientExamNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "PATIENT_EXAM_NOT_FOUND",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(InvalidExamRoomQueueRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidExamRoomQueueRequest(
            InvalidExamRoomQueueRequestException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "INVALID_EXAM_ROOM_QUEUE_REQUEST",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(DuplicateActiveExamRoomQueueException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateActiveExamRoomQueue(
            DuplicateActiveExamRoomQueueException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "DUPLICATE_ACTIVE_EXAM_ROOM_QUEUE",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(ExamRoomQueueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExamRoomQueueNotFound(
            ExamRoomQueueNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "EXAM_ROOM_QUEUE_NOT_FOUND",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(InvalidExamRoomQueueStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidExamRoomQueueStatus(
            InvalidExamRoomQueueStatusException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "INVALID_EXAM_ROOM_QUEUE_STATUS",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(CalledQueueAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCalledQueueAlreadyExists(
            CalledQueueAlreadyExistsException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "CALLED_QUEUE_ALREADY_EXISTS",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(OutOfOrderPatientExamQueueException.class)
    public ResponseEntity<ErrorResponse> handleOutOfOrderPatientExamQueue(
            OutOfOrderPatientExamQueueException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "OUT_OF_ORDER_PATIENT_EXAM_QUEUE",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                "INVALID_REQUEST_PARAMETER",
                "잘못된 요청 파라미터입니다: "
                        + exception.getName()
                        + "="
                        + exception.getValue()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}
