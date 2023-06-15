package com.example.app.app.admin.domain.serializer;

import com.example.app.app.admin.domain.vo.UserAuthority;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.EnumSet;

public class UserAuthoritySerializer extends JsonSerializer<EnumSet<UserAuthority>> {
    @Override
    public void serialize(EnumSet<UserAuthority> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeArray(value.stream()
                        .map(UserAuthority::getAuthority)
                        .toList().toArray(String[]::new),
                0, value.size());
    }
}
