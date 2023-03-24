package com.example.app.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum FilmRating {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    private final String rating;

    public static FilmRating getFilmRatingById(String rating) {
        return Objects.requireNonNull(Stream.of(FilmRating.values())
                .filter(e -> e.getRating().equals(rating))
                .findFirst()
                .orElse(null));
    }
}
