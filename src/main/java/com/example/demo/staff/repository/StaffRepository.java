package com.example.demo.staff.repository;

import com.example.demo.staff.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}
