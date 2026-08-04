package com.example.demo.examination.dto;

import com.example.demo.examination.domain.ExamCatalog;

public class ExamCatalogResponse {

    private final Long id;
    private final Long examinationRoomId;
    private final String examinationRoomName;
    private final String examinationRoomNo;
    private final String examinationRoomLocation;
    private final String code;
    private final String name;
    private final String description;
    private final boolean active;

    public ExamCatalogResponse(
            Long id,
            Long examinationRoomId,
            String examinationRoomName,
            String examinationRoomNo,
            String examinationRoomLocation,
            String code,
            String name,
            String description,
            boolean active
    ) {
        this.id = id;
        this.examinationRoomId = examinationRoomId;
        this.examinationRoomName = examinationRoomName;
        this.examinationRoomNo = examinationRoomNo;
        this.examinationRoomLocation = examinationRoomLocation;
        this.code = code;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public static ExamCatalogResponse from(ExamCatalog examCatalog) {
        return new ExamCatalogResponse(
                examCatalog.getId(),
                examCatalog.getExaminationRoom().getId(),
                examCatalog.getExaminationRoom().getName(),
                examCatalog.getExaminationRoom().getRoomNo(),
                examCatalog.getExaminationRoom().getLocation(),
                examCatalog.getCode(),
                examCatalog.getName(),
                examCatalog.getDescription(),
                examCatalog.isActive()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getExaminationRoomId() {
        return examinationRoomId;
    }

    public String getExaminationRoomName() {
        return examinationRoomName;
    }

    public String getExaminationRoomNo() {
        return examinationRoomNo;
    }

    public String getExaminationRoomLocation() {
        return examinationRoomLocation;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }
}
