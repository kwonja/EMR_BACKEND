package com.example.demo.examroomqueue.dto;

import com.example.demo.examroomqueue.domain.ExamRoomQueue;
import com.example.demo.examroomqueue.domain.ExamRoomQueueStatus;
import com.example.demo.examination.domain.ExamCatalog;
import com.example.demo.examination.domain.ExaminationRoom;
import com.example.demo.patient.domain.Patient;
import com.example.demo.patientexam.domain.PatientExam;
import com.example.demo.patientexam.domain.PatientExamStatus;
import com.example.demo.patientvisit.domain.PatientVisit;
import com.example.demo.staff.domain.Staff;

import java.time.Instant;

public record ExamRoomQueueResponse(
        Long id,
        int queueNumber,
        ExamRoomQueueStatus status,
        PatientSummary patient,
        PatientVisitSummary patientVisit,
        PatientExamSummary patientExam,
        ExamCatalogSummary examCatalog,
        ExaminationRoomSummary examinationRoom,
        StaffSummary assignedStaff,
        QueueTimes times
) {

    public static ExamRoomQueueResponse from(ExamRoomQueue queue) {
        PatientExam patientExam = queue.getPatientExam();
        PatientVisit patientVisit = patientExam.getPatientVisit();
        Patient patient = patientVisit.getPatient();
        ExamCatalog examCatalog = patientExam.getExamCatalog();
        ExaminationRoom examinationRoom = examCatalog.getExaminationRoom();

        return new ExamRoomQueueResponse(
                queue.getId(),
                queue.getQueueNumber(),
                queue.getStatus(),
                new PatientSummary(
                        patient.getId(),
                        patient.getName()
                ),
                new PatientVisitSummary(patientVisit.getId()),
                new PatientExamSummary(
                        patientExam.getId(),
                        patientExam.getStatus(),
                        patientExam.getStartedAt(),
                        patientExam.getCompletedAt()
                ),
                new ExamCatalogSummary(
                        examCatalog.getId(),
                        examCatalog.getCode(),
                        examCatalog.getName()
                ),
                new ExaminationRoomSummary(
                        examinationRoom.getId(),
                        examinationRoom.getName(),
                        examinationRoom.getRoomNo(),
                        examinationRoom.getLocation()
                ),
                StaffSummary.from(queue.getAssignedStaff()),
                new QueueTimes(
                        queue.getQueuedAt(),
                        queue.getCalledAt(),
                        queue.getEnteredAt(),
                        queue.getExitedAt()
                )
        );
    }

    public record PatientSummary(
            Long id,
            String name
    ) {
    }

    public record PatientVisitSummary(Long id) {
    }

    public record PatientExamSummary(
            Long id,
            PatientExamStatus status,
            Instant startedAt,
            Instant completedAt
    ) {
    }

    public record ExamCatalogSummary(
            Long id,
            String code,
            String name
    ) {
    }

    public record ExaminationRoomSummary(
            Long id,
            String name,
            String roomNo,
            String location
    ) {
    }

    public record StaffSummary(
            Long id,
            String name,
            DepartmentSummary department
    ) {

        private static StaffSummary from(Staff staff) {
            if (staff == null) {
                return null;
            }

            return new StaffSummary(
                    staff.getId(),
                    staff.getName(),
                    new DepartmentSummary(
                            staff.getDepartment().getId(),
                            staff.getDepartment().getName()
                    )
            );
        }
    }

    public record DepartmentSummary(
            Long id,
            String name
    ) {
    }

    public record QueueTimes(
            Instant queuedAt,
            Instant calledAt,
            Instant enteredAt,
            Instant exitedAt
    ) {
    }
}
