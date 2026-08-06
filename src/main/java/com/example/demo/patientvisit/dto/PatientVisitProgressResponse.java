package com.example.demo.patientvisit.dto;

import com.example.demo.patientexam.dto.PatientExamResponse;
import com.example.demo.patientvisit.domain.PatientVisit;
import com.example.demo.patientvisit.domain.PatientVisitStatus;

import java.util.List;

public class PatientVisitProgressResponse {

    private final Long patientVisitId;
    private final Long patientId;
    private final String patientName;
    private final PatientVisitStatus visitStatus;
    private final int totalExamCount;
    private final int completedExamCount;
    private final int remainingExamCount;
    private final PatientExamResponse inProgressExam;
    private final List<PatientExamResponse> exams;

    public PatientVisitProgressResponse(
            Long patientVisitId,
            Long patientId,
            String patientName,
            PatientVisitStatus visitStatus,
            int totalExamCount,
            int completedExamCount,
            int remainingExamCount,
            PatientExamResponse inProgressExam,
            List<PatientExamResponse> exams
    ) {
        this.patientVisitId = patientVisitId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.visitStatus = visitStatus;
        this.totalExamCount = totalExamCount;
        this.completedExamCount = completedExamCount;
        this.remainingExamCount = remainingExamCount;
        this.inProgressExam = inProgressExam;
        this.exams = exams;
    }

    public static PatientVisitProgressResponse of(
            PatientVisit patientVisit,
            int completedExamCount,
            PatientExamResponse inProgressExam,
            List<PatientExamResponse> exams
    ) {
        int totalExamCount = exams.size();

        return new PatientVisitProgressResponse(
                patientVisit.getId(),
                patientVisit.getPatient().getId(),
                patientVisit.getPatient().getName(),
                patientVisit.getStatus(),
                totalExamCount,
                completedExamCount,
                totalExamCount - completedExamCount,
                inProgressExam,
                exams
        );
    }

    public Long getPatientVisitId() {
        return patientVisitId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public PatientVisitStatus getVisitStatus() {
        return visitStatus;
    }

    public int getTotalExamCount() {
        return totalExamCount;
    }

    public int getCompletedExamCount() {
        return completedExamCount;
    }

    public int getRemainingExamCount() {
        return remainingExamCount;
    }

    public PatientExamResponse getInProgressExam() {
        return inProgressExam;
    }

    public List<PatientExamResponse> getExams() {
        return exams;
    }
}
