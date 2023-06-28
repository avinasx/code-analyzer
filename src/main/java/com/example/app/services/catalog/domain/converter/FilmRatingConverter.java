package com.example.app.services.catalog.domain.converter;

import com.example.app.common.constant.FilmRating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class FilmRatingConverter implements AttributeConverter<FilmRating, String> {
    @Override
    public String convertToDatabaseColumn(FilmRating attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException();
        }
        return attribute.getRating();
    }

    @Override
    public FilmRating convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            throw new IllegalArgumentException();
        }
        return FilmRating.RATING_MAP.get(dbData);
    }
}
