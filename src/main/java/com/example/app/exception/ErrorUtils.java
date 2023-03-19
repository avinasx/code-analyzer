package com.example.app.exception;

import com.example.app.model.error.AppError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorUtils {
    public static AppError createError(ErrorCode errorCode, HttpServletRequest request) {
        return new AppError(
                errorCode.getCode(),
                errorCode.getLevel(),
                errorCode.getPhrase(),
                request.getMethod(),
                String.valueOf(request.getRequestURL()),
                Instant.now());
    }
}
