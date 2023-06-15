package com.example.app.app.location.domain.converter;

import com.example.app.common.constant.Language;
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
class LanguageConverterTest {
    @Spy
    private LanguageConverter languageConverter;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void convertToDatabaseColumn() {
        // arrange
        var language = Language.ENGLISH;
        var expected = 1;

        // act
        var result = languageConverter.convertToDatabaseColumn(language);

        // assert
        verify(languageConverter, times(1)).convertToDatabaseColumn(language);
        assertEquals(expected, result);
    }

    @Test
    void convertToEntityAttribute() {
        // arrange
        var language = 2;
        var expected = Language.ITALIAN;

        // act
        var result = languageConverter.convertToEntityAttribute(language);

        // assert
        verify(languageConverter, times(1)).convertToEntityAttribute(language);
        assertEquals(expected, result);
    }
}
