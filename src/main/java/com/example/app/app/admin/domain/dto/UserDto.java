package com.example.app.app.admin.domain.dto;

import com.example.app.app.admin.domain.converter.UserAuthorityConverter;
import com.example.app.app.admin.domain.serializer.UserAuthoritySerializer;
import com.example.app.app.admin.domain.vo.UserAuthority;
import com.example.app.app.catalog.domain.converter.SpecialFeatureConverter;
import com.example.app.app.catalog.domain.serializer.SpecialFeatureSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.EnumSet;

@Getter
@Setter
@ToString
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @JsonProperty("customerId")
    private Integer customerId;

    @JsonProperty("email")
    @Size(min = 1, max = 50)
    private String email;

    @JsonProperty("password_hash")
    private String passwordHash;

    @JsonProperty("authority")
    @Convert(converter = UserAuthorityConverter.class)
    @JsonSerialize(using = UserAuthoritySerializer.class)
    private EnumSet<UserAuthority> authority;
}
