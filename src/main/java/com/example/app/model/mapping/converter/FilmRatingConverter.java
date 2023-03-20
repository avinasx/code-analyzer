package com.example.app.model.mapping.converter;

import com.example.app.model.enumeration.FilmRating;
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
        return FilmRating.getFilmRatingById(dbData);
    }
}
