package com.example.demo.patient.domain;


import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Entity // JPA에서 관리하는 엔티티라는걸 뜻함
@Table(name ="patients")
public class Patient {

    @Id   // pk 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 처럼 데이터테이스에 ID를 자동생성할것을 위임한다.
    private Long id;


    //unique는 중복값 저장 불가
    @Column(name = "patient_number", nullable = false,unique = true,length = 30)
    private  String patientNumber;

    @Column(name ="name", nullable = false,length = 8)
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(length = 30)
    private String phone;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )

    //Instant는 UTC 기준으로 시간을 측정함
    private Instant createdAt;

    @PrePersist
    private void initializeCreatedAt() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public Patient(String patientNumber, String name, LocalDate birthDate, String phone) {
        this.patientNumber = Objects.requireNonNull(patientNumber);
        this.name = Objects.requireNonNull(name);
        this.birthDate = birthDate;
        this.phone = phone;
    }

    protected Patient() {
    }

    public void update(String name, LocalDate birthDate, String phone) {
        if (name != null) {
            this.name = name;
        }

        if (birthDate != null) {
            this.birthDate = birthDate;
        }

        if (phone != null) {
            this.phone = phone;
        }
    }

    public Long getId() {
        return id;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
