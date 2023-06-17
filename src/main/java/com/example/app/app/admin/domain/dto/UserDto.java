package com.example.app.app.admin.domain.dto;

import com.example.app.app.admin.domain.converter.UserAuthorityConverter;
import com.example.app.app.admin.domain.serializer.UserAuthoritySerializer;
import com.example.app.app.admin.domain.vo.UserAuthority;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.EnumSet;

public class UserDto {
    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        @JsonProperty(Fields.customerId)
        private Integer customerId;

        @JsonProperty(Fields.email)
        @Size(min = 1, max = 50)
        private String email;

        @JsonProperty(Fields.passwordHash)
        private String passwordHash;

        @JsonProperty(Fields.authority)
        @Convert(converter = UserAuthorityConverter.class)
        @JsonSerialize(using = UserAuthoritySerializer.class)
        private EnumSet<UserAuthority> authority;
    }
}
