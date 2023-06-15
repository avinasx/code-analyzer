package com.example.app.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorLevel {
    INFO(0, "Info"),
    WARN(1, "Warn"),
    ERROR(2, "Error");

    private final Integer id;
    private final String name;
}
