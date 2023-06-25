package com.example.app.common.domain.error;

import com.example.app.common.constant.ErrorLevel;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
public class AppError {
    private final String code;
    private final ErrorLevel level;
    private final String phrase;
    private final String requestMethod;
    private final String requestUrl;
    private final Instant timestamp;
}
