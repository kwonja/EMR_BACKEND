# 비관적 락을 이용한 대기번호 동시성 제어

## 문제 상황

검사실 대기열을 생성할 때 해당 검사실의 현재 최대 대기번호에 1을 더해 새로운 번호를 부여합니다.

```java
int queueNumber = examRoomQueueRepository
        .findMaxQueueNumberByExaminationRoomId(examinationRoomId)
        + 1;
```

같은 검사실에 두 요청이 동시에 들어오면 두 트랜잭션이 같은 최대값을 읽을 수 있습니다.

```text
요청 A: 최대값 10 조회 → 다음 번호 11 계산 → 저장
요청 B: 최대값 10 조회 → 다음 번호 11 계산 → 저장
```

조회와 저장이 각각 정상적으로 실행되더라도 동일한 대기번호가 생성될 수 있는 경쟁 상태입니다.

## 트랜잭션만으로 부족한 이유

`@Transactional`은 대기열 생성 과정을 하나의 원자적인 작업 단위로 묶지만, 기본 격리 수준에서는 다른 트랜잭션이 같은 검사실 데이터를 동시에 읽고 계산하는 것까지 자동으로 막지 않습니다.

따라서 다음 두 가지가 모두 필요했습니다.

- 조회, 번호 계산 및 저장을 하나의 트랜잭션으로 묶기
- 같은 검사실에서 번호를 계산하는 요청끼리 순서대로 처리되도록 DB 행 잠그기

## 해결 방법

### 1. 대기열 생성 작업을 트랜잭션으로 묶기

```java
@Transactional
public ExamRoomQueueResponse create(
        ExamRoomQueueCreateRequest request
) {
    // 요청 검증, 검사실 잠금, 대기번호 계산 및 저장
}
```

서비스의 `create()`가 시작될 때 트랜잭션이 시작되고, 메서드가 정상적으로 끝나 커밋되거나 예외로 롤백될 때 종료됩니다.

### 2. 검사실 행에 비관적 쓰기 잠금 적용

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("""
        SELECT room
        FROM ExaminationRoom room
        WHERE room.id = :id
        """)
Optional<ExaminationRoom> findByIdForUpdate(
        @Param("id") Long id
);
```

`PESSIMISTIC_WRITE`는 충돌 가능성이 있다고 가정하고 조회한 행에 쓰기 잠금을 요청합니다. Hibernate와 PostgreSQL 환경에서는 개념적으로 다음과 같은 SQL로 처리됩니다.

```sql
SELECT *
FROM examination_room
WHERE id = ?
FOR UPDATE;
```

메서드 이름의 `ForUpdate`가 잠금을 만드는 것은 아닙니다. 실제 잠금은 `@Lock(LockModeType.PESSIMISTIC_WRITE)`가 요청하며, 이름은 메서드의 용도를 나타냅니다.

### 3. 잠금 획득 후 대기번호 계산

```java
ExaminationRoom examinationRoom = examinationRoomRepository
        .findByIdForUpdate(examinationRoomId)
        .orElseThrow(() -> new ExaminationRoomNotFoundException(
                examinationRoomId
        ));

int queueNumber = examRoomQueueRepository
        .findMaxQueueNumberByExaminationRoomId(examinationRoomId)
        + 1;
```

잠글 대상이 없을 수도 있는 대기열 행이나 `MAX()` 집계 결과가 아니라, 항상 존재하는 검사실 행을 잠금 대상으로 사용했습니다. 같은 검사실의 대기번호를 만드는 모든 요청이 같은 행의 잠금을 획득해야 하므로 요청이 순차적으로 처리됩니다.

## 동작 과정

```text
요청 A: 트랜잭션 시작 → 검사실 행 잠금 → 최대값 조회 → 저장 → 커밋
요청 B: 트랜잭션 시작 → 잠금 대기 ───────────────────→ 잠금 획득
                                                        → 최신 최대값 조회
                                                        → 저장 → 커밋
```

요청 A가 11번을 저장하고 커밋한 후 요청 B가 잠금을 획득합니다. 요청 B는 최신 최대값인 11을 조회해 12번을 생성합니다. 잠금은 트랜잭션이 커밋되거나 롤백될 때 해제됩니다.

## 적용 결과

- 동일한 검사실에 들어오는 대기열 생성 요청을 순차 처리
- 최대 대기번호 조회와 다음 번호 저장 사이의 경쟁 상태 방지
- 서로 다른 검사실은 서로 다른 행을 잠그므로 독립적으로 처리 가능

## 고려 사항

비관적 락은 데이터 정합성을 높이는 대신 잠금 대기 시간과 데이터베이스 부하를 만들 수 있습니다. 트랜잭션 안에서 외부 API 호출이나 오래 걸리는 작업을 수행하지 않고 잠금 범위를 짧게 유지해야 합니다.

또한 같은 대기번호를 만드는 모든 코드 경로가 반드시 검사실 행의 잠금을 먼저 획득해야 합니다. 우회 경로가 있으면 동일한 경쟁 상태가 다시 발생할 수 있습니다. 데이터베이스에 검사실과 대기번호의 복합 유니크 제약을 추가하면 애플리케이션 잠금과 별도로 최종 정합성을 한 번 더 보호할 수 있습니다.
