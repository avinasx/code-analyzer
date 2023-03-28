package com.example.app.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum SpecialFeature {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private final String feature;

    public static SpecialFeature getSpecialFeatureById(String id) {
        return Objects.requireNonNull(Stream.of(SpecialFeature.values())
                .filter(e -> e.getFeature().equals(id))
                .findFirst()
                .orElse(null));
    }
}
