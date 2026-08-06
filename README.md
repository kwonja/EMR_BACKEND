# 검진센터 관리 API

환자의 예약과 방문 접수부터 검사항목 배정, 검사실 대기열 등록 및 검사 완료까지의 흐름을 관리하는 Spring Boot 기반 REST API 프로젝트입니다.

## 기술 스택 및 버전

| 구분 | 기술 및 버전 |
| --- | --- |
| 프로젝트 버전 | 0.0.1-SNAPSHOT |
| Java | 17 |
| Spring Boot | 4.1.0 |
| Gradle | 9.5.1 |
| ORM | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| 기타 | Lombok |

Hibernate와 PostgreSQL JDBC Driver의 세부 버전은 Spring Boot의 의존성 관리 설정을 따릅니다.

## 주요 기능

- 환자, 부서 및 직원 관리
- 환자 예약과 방문 접수 관리
- 방문별 검사항목 및 검사 순서 관리
- 검사실과 검사 카탈로그 관리
- 검사실별 대기열 등록, 호출, 입장 및 완료 처리
- 방문 및 검사 진행 상태 조회

## 프로젝트 구조

기능별 패키지를 먼저 나누고, 각 패키지 안에 MVC 계층을 배치했습니다.

```text
src/main/java/com/example/demo
├── appointment/          # 예약
├── patient/              # 환자
├── patientvisit/         # 환자 방문
├── patientexam/          # 환자별 검사항목
├── examination/          # 검사실 및 검사 카탈로그
├── examroomqueue/        # 검사실 대기열
├── staff/                # 직원
├── department/           # 부서
└── common/               # 공통 예외 처리
```

각 기능 패키지는 다음 계층으로 구성됩니다.

```text
기능 패키지
├── controller/           # HTTP 요청 및 응답 처리
├── service/              # 비즈니스 로직과 트랜잭션 경계
├── repository/           # JPA 기반 데이터 접근
├── domain/               # 엔티티와 도메인 상태 변경
├── dto/                  # API 요청 및 응답 모델
└── exception/            # 기능별 예외
```

### 요청 처리 흐름

```text
Client
  → Controller
  → Service
  → Repository
  → PostgreSQL
```

- Controller는 요청을 받고 DTO를 통해 응답합니다.
- Service는 검증과 상태 변경을 수행하고 트랜잭션 범위를 정의합니다.
- Repository는 Spring Data JPA와 JPQL을 사용해 데이터를 조회하고 저장합니다.
- Domain은 엔티티의 상태와 상태 변경 규칙을 관리합니다.

## 문제 해결

### Fetch Join을 이용한 N+1 문제 해결

검사 대기열과 환자 검사항목 목록을 응답 DTO로 변환할 때 연관 엔티티마다 추가 SELECT가 실행되는 문제를 확인했습니다. 조회에 필요한 연관관계를 Fetch Join으로 함께 가져오도록 변경해 `1 + N`번 발생하던 조회를 하나의 쿼리로 줄였습니다.

[N+1 문제 해결 과정](ISSUE/01-n-plus-one-fetch-join.md)

### 비관적 락을 이용한 대기번호 동시성 제어

동시에 접수된 요청이 같은 검사실의 최대 대기번호를 읽으면 동일한 다음 번호를 생성할 수 있습니다. 대기열 생성 전체를 하나의 트랜잭션으로 묶고, 검사실 행을 `PESSIMISTIC_WRITE`로 잠근 후 대기번호를 계산하도록 변경했습니다.

[동시성 문제 해결 과정](ISSUE/02-concurrency-pessimistic-lock.md)

### WebSocket을 이용한 대기열 이벤트 전파

대기열 등록, 호출, 입장 및 완료 상태가 변경되면 트랜잭션 커밋 이후 전체 관제 화면과 해당 검사실 화면에 이벤트를 전달합니다. 하나의 WebSocket 연결에서 전체 Topic과 검사실별 Topic을 구독할 수 있도록 구성했습니다.

[WebSocket 구독 구조와 이벤트 전파 과정](ISSUE/03-websocket-event-subscription.md)

## 공통 API 응답

모든 API는 `ApiResponse<T>`를 사용해 성공 여부, 메시지와 응답 데이터를 동일한 형식으로 반환합니다.

성공 응답:

```json
{
  "success": true,
  "message": "환자 조회 성공",
  "data": {
    "id": 1,
    "name": "홍길동"
  }
}
```

실패 응답:

```json
{
  "success": false,
  "code": "PATIENT_NOT_FOUND",
  "message": "환자를 찾을 수 없습니다.",
  "data": null
}
```

실패 응답은 JSON의 `success` 값뿐 아니라 `400 Bad Request`, `404 Not Found`, `409 Conflict` 등 상황에 맞는 HTTP 상태 코드도 함께 반환합니다.

## 실행 환경 설정

데이터베이스 연결 정보는 환경 변수로 전달할 수 있습니다.

| 환경 변수 | 기본값 |
| --- | --- |
| `DB_URL` | `jdbc:postgresql://localhost:5432/demo` |
| `DB_USERNAME` | `postgres` |
| `DB_PASSWORD` | 기본값 없음 |

Windows에서는 다음 명령으로 애플리케이션을 실행합니다.

```powershell
.\gradlew.bat bootRun
```
