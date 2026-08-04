package com.example.demo.examination.repository;

import com.example.demo.examination.domain.ExamCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExamCatalogRepository extends JpaRepository<ExamCatalog, Long> {

    boolean existsByCode(String code);

    @Query("""
            SELECT exam
            FROM ExamCatalog exam
            JOIN FETCH exam.examinationRoom
            """)
    List<ExamCatalog> findAllWithExaminationRoom();

    @Query("""
            SELECT exam
            FROM ExamCatalog exam
            JOIN FETCH exam.examinationRoom
            WHERE exam.id = :id
            """)
    Optional<ExamCatalog> findByIdWithExaminationRoom(
            @Param("id") Long id
    );
}
