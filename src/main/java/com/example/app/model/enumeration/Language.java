package com.example.app.model.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Language {
    NONE(0, "-"),
    ENGLISH(1, "English"),
    ITALIAN(2, "Italian"),
    JAPANESE(3, "Japanese"),
    MANDARIN(4, "Mandarin"),
    FRENCH(5, "French"),
    GERMAN(6, "German");

    private final Integer id;
    private final String language;

    public static Language getLanguageById(Integer id) {
        return Objects.requireNonNull(Stream.of(Language.values())
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null));
    }

    @JsonValue
    public String getLanguage() {
        return language;
    }
}
