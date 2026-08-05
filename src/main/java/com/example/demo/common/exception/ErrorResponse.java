package com.example.demo.common.exception;

import com.example.demo.common.response.ApiResponse;

public class ErrorResponse extends ApiResponse<Void> {

    public ErrorResponse(String code, String message) {
        super(false, code, message, null);
    }
}
