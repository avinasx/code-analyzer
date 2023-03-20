package com.example.app.model.mapping.converter;

import com.example.app.model.enumeration.SpecialFeature;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class SpecialFeatureConverter implements AttributeConverter<Set<SpecialFeature>, String> {
    @Override
    public String convertToDatabaseColumn(Set<SpecialFeature> attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<SpecialFeature> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Arrays.stream(dbData.split(","))
                .map(featureString -> Arrays.stream(SpecialFeature.values())
                        .filter(feature -> feature.getFeature().equals(featureString))
                        .findFirst()
                        .orElse(null))
                .collect(Collectors.toSet());
    }
}
