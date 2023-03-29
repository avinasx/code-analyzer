package com.example.app.model.internal.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullName {
    @JsonProperty("firstName")
    @Size(min = 1, max = 45)
    private String firstName;

    @JsonProperty("lastName")
    @Size(min = 1, max = 45)
    private String lastName;
}
