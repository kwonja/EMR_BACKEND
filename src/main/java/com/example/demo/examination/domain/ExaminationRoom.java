package com.example.demo.examination.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "examination_rooms")
public class ExaminationRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "room_no", nullable = false, length = 30)
    private String roomNo;

    @Column(nullable = false, length = 100)
    private String location;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    protected ExaminationRoom() {
    }

    public ExaminationRoom(
            String name,
            String roomNo,
            String location,
            String description
    ) {
        this.name = Objects.requireNonNull(name);
        this.roomNo = Objects.requireNonNull(roomNo);
        this.location = Objects.requireNonNull(location);
        this.description = description;
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
