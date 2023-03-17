package com.example.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    GENERIC_ERROR("CODE-0001", "ERROR", "Generic Error"),
    GENERIC_WARN("CODE-0002", "WARN", "Generic Warning");

    private final String code;
    private final String level;
    private final String phrase;
}
