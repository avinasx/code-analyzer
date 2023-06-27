package com.example.app.common.exception;

import com.example.app.common.constant.ErrorCode;
import com.example.app.common.domain.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity.HeadersBuilder<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        var code = ErrorCode.RESOURCE_NOT_AVAILABLE;
        log.error(code.getPhrase() + ex);
        return ResponseEntity.of(ResponseDto.of(code));
    }

    @ExceptionHandler(ResourceNotAvailableException.class)
    public ResponseEntity.HeadersBuilder<?> handleResourceNotAvailableException(ResourceNotAvailableException ex) {
        var code = ErrorCode.RESOURCE_NOT_AVAILABLE;
        log.error(code.getPhrase() + ex);
        return ResponseEntity.of(ResponseDto.of(code));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity.HeadersBuilder<?> handleException(Exception ex) {
        var code = ErrorCode.GENERIC_ERROR;
        log.error(code.getPhrase() + ex);
        return ResponseEntity.of(ResponseDto.of(code));
    }
}
