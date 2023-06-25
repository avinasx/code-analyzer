package com.example.app.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
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
