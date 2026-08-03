package com.example.demo.staff.domain;

import com.example.demo.department.domain.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "staff_type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private StaffType staffType;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    protected Staff() {
    }

    public Staff(Department department, String name, StaffType staffType) {
        this.department = Objects.requireNonNull(department);
        this.name = Objects.requireNonNull(name);
        this.staffType = Objects.requireNonNull(staffType);
    }

    public void update(
            Department department,
            String name,
            StaffType staffType,
            Boolean active
    ) {
        if (department != null) {
            this.department = department;
        }

        if (name != null) {
            this.name = name;
        }

        if (staffType != null) {
            this.staffType = staffType;
        }

        if (active != null) {
            this.active = active;
        }
    }

    public Long getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    public boolean isActive() {
        return active;
    }
}
