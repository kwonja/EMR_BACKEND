package com.example.demo.common.logging;

import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class RequestLoggingFilterTest {

    private final RequestLoggingFilter filter = new RequestLoggingFilter();

    @Test
    void 전달받은_request_id를_MDC와_응답_헤더에_사용한다() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/patients");
        request.addHeader(RequestLoggingFilter.REQUEST_ID_HEADER, "patient-test-001");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, (servletRequest, servletResponse) ->
                assertEquals(
                        "patient-test-001",
                        MDC.get(RequestLoggingFilter.REQUEST_ID_MDC_KEY)
                )
        );

        assertEquals(
                "patient-test-001",
                response.getHeader(RequestLoggingFilter.REQUEST_ID_HEADER)
        );
        assertNull(MDC.get(RequestLoggingFilter.REQUEST_ID_MDC_KEY));
    }

    @Test
    void request_id가_없으면_생성한다() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/patients");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, (servletRequest, servletResponse) -> {
            String requestId = MDC.get(RequestLoggingFilter.REQUEST_ID_MDC_KEY);
            assertFalse(requestId.isBlank());
        });

        assertFalse(
                response.getHeader(RequestLoggingFilter.REQUEST_ID_HEADER).isBlank()
        );
        assertNull(MDC.get(RequestLoggingFilter.REQUEST_ID_MDC_KEY));
    }
}
