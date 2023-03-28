package com.example.app.model.mapping.converter;

import com.example.app.model.constant.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Slf4j
class CategoryConverterTest {
    @Spy
    private CategoryConverter categoryConverter;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void convertToDatabaseColumn() {
        // arrange
        var category = Category.ACTION;
        var expected = 1;

        // act
        var result = categoryConverter.convertToDatabaseColumn(category);

        // assert
        verify(categoryConverter, times(1)).convertToDatabaseColumn(category);
        assertEquals(expected, result);
    }

    @Test
    void convertToEntityAttribute() {
        // arrange
        var category = 2;
        var expected = Category.ANIMATION;

        // act
        var result = categoryConverter.convertToEntityAttribute(category);

        // assert
        verify(categoryConverter, times(1)).convertToEntityAttribute(category);
        assertEquals(expected, result);
    }
}
