package com.example.app.model.mapping.serializer;

import com.example.app.model.constant.SpecialFeature;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.EnumSet;

public class SpecialFeatureSerializer extends JsonSerializer<EnumSet<SpecialFeature>> {
    @Override
    public void serialize(EnumSet<SpecialFeature> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeArray(value.stream()
                            .map(SpecialFeature::getFeature)
                            .toList().toArray(String[]::new),
                    0, value.size());
        }
    }
}
