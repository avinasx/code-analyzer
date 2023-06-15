package com.example.app.common.exception;

import com.example.app.common.constant.ErrorCode;
import com.example.app.common.domain.error.AppError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AppError> handleResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException ex) {
        log.error(ErrorCode.RESOURCE_NOT_FOUND.getPhrase() + ": {}", ex.getMessage());
        ex.printStackTrace();
        var appError = ErrorUtils.createError(ErrorCode.RESOURCE_NOT_FOUND, request);
        return new ResponseEntity<>(appError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppError> handleException(HttpServletRequest request, Exception ex) {
        log.error(ErrorCode.GENERIC_ERROR.getPhrase() + ": {}", ex.getMessage());
        ex.printStackTrace();
        var appError = ErrorUtils.createError(ErrorCode.GENERIC_ERROR, request);
        return new ResponseEntity<>(appError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
