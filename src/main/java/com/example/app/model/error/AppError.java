package com.example.app.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppError {
    private String code;
    private String level;
    private String phrase;
    private Integer statusCode;
    private String requestMethod;
    private String requestUrl;
    private Instant timestamp;
}
