package com.example.app.exception;

import com.example.app.exception.constant.ErrorCode;
import com.example.app.model.internal.error.AppError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorUtils {
    public static AppError createError(ErrorCode errorCode, HttpServletRequest request) {
        return AppError.builder()
                .code(errorCode.getCode())
                .level(errorCode.getLevel())
                .phrase(errorCode.getPhrase())
                .requestMethod(request.getMethod())
                .requestUrl(request.getRequestURL().toString())
                .timestamp(Instant.now())
                .build();
    }
}
