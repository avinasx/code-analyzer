package com.example.app.model.internal.core;

import com.example.app.model.constant.FilmRating;
import com.example.app.model.constant.Language;
import com.example.app.model.constant.SpecialFeature;
import com.example.app.model.entity.FilmActorEntity;
import com.example.app.model.entity.FilmCategoryEntity;
import com.example.app.model.entity.InventoryEntity;
import com.example.app.model.entity.LanguageEntity;
import com.example.app.model.mapping.converter.FilmRatingConverter;
import com.example.app.model.mapping.converter.SpecialFeatureConverter;
import com.example.app.model.mapping.serializer.SpecialFeatureSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.EnumSet;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmModel {
    @JsonProperty("filmId")
    private Integer filmId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("releaseYear")
    private LocalDate releaseYear;

    @JsonProperty("languageId")
    private Language languageId;

    @JsonProperty("originalLanguageId")
    private Language originalLanguageId;

    @JsonProperty("rentalDuration")
    private Integer rentalDuration;

    @JsonProperty("rentalRate")
    private BigDecimal rentalRate;

    @JsonProperty("length")
    private Integer length;

    @JsonProperty("replacementCost")
    private BigDecimal replacementCost;

    @JsonProperty("rating")
    @Convert(converter = FilmRatingConverter.class)
    private FilmRating rating;

    @JsonProperty("specialFeatures")
    @Convert(converter = SpecialFeatureConverter.class)
    @JsonSerialize(using = SpecialFeatureSerializer.class)
    private EnumSet<SpecialFeature> specialFeatures;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("languageByLanguageId")
    private LanguageEntity languageByLanguageId;

    @JsonIgnore
    @JsonProperty("languageByOriginalLanguageId")
    private LanguageEntity languageByOriginalLanguageId;

    @JsonIgnore
    @JsonProperty("filmActorsByFilmId")
    private Collection<FilmActorEntity> filmActorsByFilmId;

    @JsonIgnore
    @JsonProperty("filmCategoriesByFilmId")
    private Collection<FilmCategoryEntity> filmCategoriesByFilmId;

    @JsonIgnore
    @JsonProperty("inventoriesByFilmId")
    private Collection<InventoryEntity> inventoriesByFilmId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmModel that = (FilmModel) o;
        return Objects.equal(filmId, that.filmId)
                && Objects.equal(title, that.title)
                && Objects.equal(description, that.description)
                && Objects.equal(releaseYear, that.releaseYear)
                && languageId == that.languageId
                && originalLanguageId == that.originalLanguageId
                && Objects.equal(rentalDuration, that.rentalDuration)
                && Objects.equal(rentalRate, that.rentalRate)
                && Objects.equal(length, that.length)
                && Objects.equal(replacementCost, that.replacementCost)
                && rating == that.rating
                && Objects.equal(specialFeatures, that.specialFeatures)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(filmId, title, description, releaseYear,
                languageId, originalLanguageId, rentalDuration, rentalRate, length,
                replacementCost, rating, specialFeatures, lastUpdate);
    }
}
