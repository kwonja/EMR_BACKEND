package com.example.demo.examination.repository;

import com.example.demo.examination.domain.ExamCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamCatalogRepository extends JpaRepository<ExamCatalog, Long> {

    boolean existsByCode(String code);
}
