package com.example.app.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    GENERIC_ERROR("CODE-0001", ErrorLevel.ERROR, "Generic error"),
    GENERIC_WARN("CODE-0002", ErrorLevel.WARN, "Generic warning"),
    RESOURCE_NOT_FOUND("CODE-0003", ErrorLevel.ERROR, "Resource not found");

    private final String code;
    private final ErrorLevel level;
    private final String phrase;
}
