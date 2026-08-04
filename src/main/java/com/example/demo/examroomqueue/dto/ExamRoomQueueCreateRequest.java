package com.example.demo.examroomqueue.dto;

public class ExamRoomQueueCreateRequest {

    private Long patientExamId;

    public ExamRoomQueueCreateRequest() {
    }

    public Long getPatientExamId() {
        return patientExamId;
    }

    public void setPatientExamId(Long patientExamId) {
        this.patientExamId = patientExamId;
    }

}
