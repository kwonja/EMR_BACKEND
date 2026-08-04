package com.example.demo.examination.dto;

import com.example.demo.examination.domain.ExaminationRoom;

public class ExaminationRoomResponse {

    private final Long id;
    private final String name;
    private final String roomNo;
    private final String location;
    private final String description;
    private final boolean active;

    public ExaminationRoomResponse(
            Long id,
            String name,
            String roomNo,
            String location,
            String description,
            boolean active
    ) {
        this.id = id;
        this.name = name;
        this.roomNo = roomNo;
        this.location = location;
        this.description = description;
        this.active = active;
    }

    public static ExaminationRoomResponse from(ExaminationRoom examinationRoom) {
        return new ExaminationRoomResponse(
                examinationRoom.getId(),
                examinationRoom.getName(),
                examinationRoom.getRoomNo(),
                examinationRoom.getLocation(),
                examinationRoom.getDescription(),
                examinationRoom.isActive()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }
}
