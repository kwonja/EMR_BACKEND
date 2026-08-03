package com.example.demo.department.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "department_type", nullable = false, length = 30)
    private String departmentType;

    protected Department() {
    }

    public Department(String name, String departmentType) {
        this.name = Objects.requireNonNull(name);
        this.departmentType = Objects.requireNonNull(departmentType);
    }

    public void update(String name, String departmentType) {
        if (name != null) {
            this.name = name;
        }

        if (departmentType != null) {
            this.departmentType = departmentType;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartmentType() {
        return departmentType;
    }
}
