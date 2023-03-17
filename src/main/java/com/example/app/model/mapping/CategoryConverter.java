package com.example.app.model.mapping;

import com.example.app.model.enumeration.Category;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CategoryConverter implements AttributeConverter<Category, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Category attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException();
        }
        return attribute.getId();
    }

    @Override
    public Category convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            throw new IllegalArgumentException();
        }
        return Category.getCategoryById(dbData);
    }
}
