package com.example.app.app.admin.domain.converter;

import com.example.app.app.admin.domain.vo.UserAuthority;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Converter
public class UserAuthorityConverter implements AttributeConverter<EnumSet<UserAuthority>, String> {
    @Override
    public String convertToDatabaseColumn(EnumSet<UserAuthority> attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException();
        }
        return attribute.stream()
                .map(UserAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    @Override
    public EnumSet<UserAuthority> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            throw new IllegalArgumentException();
        }
        return Arrays.stream(dbData.split(","))
                .map(authorityString -> Arrays.stream(UserAuthority.values())
                        .filter(authority -> authority.getAuthority().equals(authorityString))
                        .findFirst()
                        .orElse(null))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(UserAuthority.class)));
    }
}
