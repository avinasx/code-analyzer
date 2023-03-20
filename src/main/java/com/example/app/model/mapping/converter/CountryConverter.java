package com.example.app.model.mapping.converter;

import com.example.app.model.enumeration.Country;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CountryConverter implements AttributeConverter<Country, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Country attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException();
        }
        return attribute.getId();
    }

    @Override
    public Country convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            throw new IllegalArgumentException();
        }
        return Country.getCountryById(dbData);
    }
}
