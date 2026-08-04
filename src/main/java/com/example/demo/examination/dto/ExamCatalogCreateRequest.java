package com.example.demo.examination.dto;

public class ExamCatalogCreateRequest {

    private Long examinationRoomId;
    private String code;
    private String name;
    private String description;

    public ExamCatalogCreateRequest() {
    }

    public Long getExaminationRoomId() {
        return examinationRoomId;
    }

    public void setExaminationRoomId(Long examinationRoomId) {
        this.examinationRoomId = examinationRoomId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
