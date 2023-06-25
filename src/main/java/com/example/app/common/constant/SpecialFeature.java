package com.example.app.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum SpecialFeature {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private final String feature;

    public static SpecialFeature getSpecialFeatureById(String feature) {
        return Objects.requireNonNull(Stream.of(SpecialFeature.values())
                .filter(e -> e.getFeature().equals(feature))
                .findFirst()
                .orElse(null));
    }
}
