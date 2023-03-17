package com.example.app.model.error;

import com.example.app.exception.ErrorLevel;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppError {
    private String code;
    private ErrorLevel level;
    private String phrase;
    private String requestMethod;
    private String requestUrl;
    private Instant timestamp;
}
