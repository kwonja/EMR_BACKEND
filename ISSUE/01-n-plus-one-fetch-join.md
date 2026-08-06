# Fetch Join을 이용한 N+1 문제 해결

## 문제 상황

검사실 대기열 목록을 조회한 뒤 각 엔티티를 응답 DTO로 변환하는 과정에서 환자 검사, 환자 방문, 환자, 검사항목 및 검사실 정보를 함께 사용합니다.

```java
for (ExamRoomQueue queue : queues) {
    responses.add(ExamRoomQueueResponse.from(queue));
}
```

일반적인 목록 조회만 사용하면 최초 대기열 조회 이후 연관 엔티티가 필요할 때마다 추가 SELECT가 실행될 수 있습니다. 목록에 N개의 결과가 있다면 최초 조회 1번과 연관관계 조회 N번이 이어지는 N+1 문제가 발생합니다.

```text
대기열 목록 조회                         1번
각 대기열에서 필요한 연관 엔티티 조회    N번
---------------------------------------------
총 쿼리 수                              1 + N번
```

JPA 연관관계의 `EAGER` 설정도 JPQL 목록 조회에서 항상 하나의 조인 SQL을 보장하지 않으므로 N+1 문제의 해결책이 되지 않습니다.

## 원인

응답에는 여러 연관 엔티티의 정보가 필요하지만, 기본 목록 조회는 어떤 연관관계를 같은 SQL에서 가져와야 하는지 명시하지 않습니다. Hibernate는 조회 결과를 DTO로 변환하며 필요한 연관관계를 개별적으로 조회할 수 있고, 목록의 크기가 커질수록 SQL 실행 횟수도 증가합니다.

## 해결 방법

목록 응답에 반드시 필요한 연관관계를 JPQL의 `JOIN FETCH`로 명시했습니다.

```java
@Query("""
        SELECT queue
        FROM ExamRoomQueue queue
        JOIN FETCH queue.patientExam patientExam
        JOIN FETCH patientExam.patientVisit patientVisit
        JOIN FETCH patientVisit.patient
        JOIN FETCH patientExam.examCatalog examCatalog
        JOIN FETCH examCatalog.examinationRoom room
        LEFT JOIN FETCH queue.assignedStaff
        WHERE (:examinationRoomId IS NULL
                OR room.id = :examinationRoomId)
          AND (:status IS NULL OR queue.status = :status)
        ORDER BY queue.queueNumber
        """)
List<ExamRoomQueue> findAllWithDetails(
        Long examinationRoomId,
        ExamRoomQueueStatus status
);
```

환자 검사항목 목록에도 같은 방식을 적용했습니다.

```java
@Query("""
        SELECT patientExam
        FROM PatientExam patientExam
        JOIN FETCH patientExam.examCatalog examCatalog
        JOIN FETCH examCatalog.examinationRoom
        WHERE patientExam.patientVisit.id = :patientVisitId
          AND (:status IS NULL OR patientExam.status = :status)
        ORDER BY patientExam.sequenceNumber
        """)
List<PatientExam> findAllWithDetailsByPatientVisitId(
        Long patientVisitId,
        PatientExamStatus status
);
```

## 개선 결과

Fetch Join을 사용하면 목록과 응답 생성에 필요한 연관 데이터를 하나의 조인 쿼리로 가져옵니다.

```text
변경 전: 목록 조회 1번 + 연관 엔티티 추가 조회 N번
변경 후: Fetch Join 조회 1번
```

이를 통해 목록 크기에 비례해 증가하던 데이터베이스 왕복 횟수를 줄이고, 조회 성능을 예측하기 쉽게 만들었습니다.

## 적용 위치

- `ExamRoomQueueRepository.findAllWithDetails()`
- `PatientExamRepository.findAllWithDetailsByPatientVisitId()`
- `ExamCatalogRepository`의 상세 정보 조회

## 고려 사항

Fetch Join이 항상 최선인 것은 아닙니다. 조인 대상이 많으면 한 번에 전송되는 데이터가 커질 수 있고, 일대다 컬렉션 Fetch Join은 결과 행을 증가시켜 페이징과 함께 사용하기 어렵습니다.

페이지네이션이 필요한 컬렉션 조회에서는 부모 엔티티를 먼저 페이지 단위로 조회한 뒤, 연관 데이터를 Batch Fetch로 묶어서 조회할 수 있습니다. 이 방식은 Fetch Join으로 인해 결과 행이 증가하는 문제를 피하면서도 연관 데이터를 건별로 조회하는 N+1 문제를 줄일 수 있습니다.

```text
1. 부모 목록 페이지 조회
2. 조회된 부모 ID를 기준으로 연관 데이터 일괄 조회

SELECT ... FROM parent LIMIT ? OFFSET ?;
SELECT ... FROM child WHERE parent_id IN (?, ?, ...);
```

이 프로젝트의 목록 조회에서는 응답에 필요한 다대일 연관관계를 명확히 알고 있으므로 Fetch Join을 선택했습니다. 페이지네이션이 필요하다면 페이지 조회와 Batch Fetch를 조합하고, 조회 화면마다 필요한 데이터가 다르다면 DTO 직접 조회나 EntityGraph도 함께 검토할 수 있습니다.
