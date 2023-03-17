package com.example.app.exception;

import com.example.app.model.error.AppError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@NoArgsConstructor
public class ErrorUtil {
    public static AppError createError(ErrorCode errorCode, HttpStatus status, HttpServletRequest request) {
        return new AppError(
                errorCode.getCode(),
                errorCode.getLevel(),
                errorCode.getPhrase(),
                status.value(),
                request.getMethod(),
                String.valueOf(request.getRequestURL()),
                Instant.now());
    }
}
