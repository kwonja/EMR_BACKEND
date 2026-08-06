package com.example.demo.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "EMR Backend API",
                version = "v1",
                description = """
                        검진센터 EMR Backend REST API 문서입니다.

                        ## 대기열 WebSocket

                        - 연결 경로: `/rfid/ws`
                        - 전체 대기열 구독: `/event/wait-queue`
                        - 검사실별 구독: `/event/wait-queue/examination-rooms/{examinationRoomId}`

                        대기열 등록, 호출, 입장 및 검사 완료 시 다음 이벤트가 전송됩니다.

                        - `QUEUE_CREATED`
                        - `QUEUE_CALLED`
                        - `QUEUE_ENTERED`
                        - `QUEUE_COMPLETED`

                        WebSocket 구독은 `/websocket-test.html`에서 테스트할 수 있습니다.
                        """
        )
)
public class OpenApiConfig {
}
