package com.example.app.app.catalog.domain.dto;

import com.example.app.app.catalog.domain.converter.CategoryConverter;
import com.example.app.app.location.domain.dto.LanguageDto;
import com.example.app.common.constant.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;

public class FilmDto {
    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Film {
        @JsonProperty(Fields.filmId)
        private Integer filmId;

        @JsonProperty(Fields.title)
        @Size(min = 1, max = 128)
        private String title;

        @JsonProperty(Fields.description)
        @Size(max = 65535)
        private String description;

        @JsonProperty(Fields.releaseYear)
        private LocalDate releaseYear;

        @JsonProperty(Fields.languageId)
        private Language languageId;

        @JsonProperty(Fields.originalLanguageId)
        private Language originalLanguageId;

        @JsonProperty(Fields.rentalDuration)
        private Integer rentalDuration;

        @JsonProperty(Fields.rentalRate)
        private BigDecimal rentalRate;

        @JsonProperty(Fields.length)
        private Integer length;

        @JsonProperty(Fields.replacementCost)
        private BigDecimal replacementCost;

        @JsonProperty(Fields.rating)
        private FilmRating rating;

        @JsonProperty(Fields.specialFeatures)
        private EnumSet<SpecialFeature> specialFeatures;

        @JsonProperty(Fields.lastUpdate)
        private LocalDateTime lastUpdate;

        @JsonIgnore
        @JsonProperty(Fields.languageByLanguageId)
        @ToString.Exclude
        private LanguageDto.Language languageByLanguageId;

        @JsonIgnore
        @JsonProperty(Fields.languageByOriginalLanguageId)
        @ToString.Exclude
        private LanguageDto.Language languageByOriginalLanguageId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Film that = (Film) o;
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

    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilmRequest implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @JsonProperty(Fields.title)
        @Size(min = 1, max = 128)
        private String title;

        @JsonProperty(Fields.description)
        @Size(max = 65535)
        private String description;

        @JsonProperty(Fields.releaseYear)
        private LocalDate releaseYear;

        @JsonProperty(Fields.languageId)
        private Language languageId;

        @JsonProperty(Fields.originalLanguageId)
        private Language originalLanguageId;

        @JsonProperty(Fields.rentalDuration)
        private Integer rentalDuration;

        @JsonProperty(Fields.rentalRate)
        private BigDecimal rentalRate;

        @JsonProperty(Fields.length)
        private Integer length;

        @JsonProperty(Fields.replacementCost)
        private BigDecimal replacementCost;

        @JsonProperty(Fields.rating)
        private FilmRating rating;

        @JsonProperty(Fields.specialFeatures)
        private EnumSet<SpecialFeature> specialFeatures;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final FilmRequest that = (FilmRequest) o;
            return Objects.equal(title, that.title)
                    && Objects.equal(description, that.description)
                    && Objects.equal(releaseYear, that.releaseYear)
                    && languageId == that.languageId
                    && originalLanguageId == that.originalLanguageId
                    && Objects.equal(rentalDuration, that.rentalDuration)
                    && Objects.equal(rentalRate, that.rentalRate)
                    && Objects.equal(length, that.length)
                    && Objects.equal(replacementCost, that.replacementCost)
                    && rating == that.rating
                    && Objects.equal(specialFeatures, that.specialFeatures);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(title, description, releaseYear, languageId, originalLanguageId,
                    rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures);
        }
    }

    @Relation(collectionRelation = HalRelation.Fields.filmList,
            itemRelation = HalRelation.Fields.film)
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilmResponse extends RepresentationModel<FilmResponse> implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @JsonUnwrapped
        private Film film;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            final FilmResponse that = (FilmResponse) o;
            return Objects.equal(film, that.film);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(super.hashCode(), film);
        }
    }

    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilmCategory {
        @JsonProperty(Fields.filmId)
        private Integer filmId;

        @JsonProperty(Fields.categoryId)
        @Convert(converter = CategoryConverter.class)
        private Category categoryId;

        @JsonProperty(Fields.lastUpdate)
        private LocalDateTime lastUpdate;

        @JsonIgnore
        @JsonProperty(Fields.filmByFilmId)
        @ToString.Exclude
        private Film filmByFilmId;

        @JsonIgnore
        @JsonProperty(Fields.categoryByCategoryId)
        @ToString.Exclude
        private CategoryDto.Category categoryByCategoryId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final FilmCategory that = (FilmCategory) o;
            return Objects.equal(filmId, that.filmId)
                    && categoryId == that.categoryId
                    && Objects.equal(lastUpdate, that.lastUpdate);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(filmId, categoryId, lastUpdate);
        }
    }

    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilmActor {
        @JsonProperty(Fields.actorId)
        private Integer actorId;

        @JsonProperty(Fields.filmId)
        private Integer filmId;

        @JsonProperty(Fields.lastUpdate)
        private LocalDateTime lastUpdate;

        @JsonIgnore
        @JsonProperty(Fields.actorByActorId)
        @ToString.Exclude
        private ActorDto.Actor actorByActorId;

        @JsonIgnore
        @JsonProperty(Fields.filmByFilmId)
        @ToString.Exclude
        private Film filmByFilmId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final FilmActor that = (FilmActor) o;
            return Objects.equal(actorId, that.actorId)
                    && Objects.equal(filmId, that.filmId)
                    && Objects.equal(lastUpdate, that.lastUpdate);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(actorId, filmId, lastUpdate);
        }
    }

    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilmText {
        @JsonProperty(Fields.filmId)
        private Integer filmId;

        @JsonProperty(Fields.title)
        @Size(min = 1, max = 255)
        private String title;

        @JsonProperty(Fields.description)
        @Size(max = 65535)
        private String description;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final FilmText that = (FilmText) o;
            return Objects.equal(filmId, that.filmId)
                    && Objects.equal(title, that.title)
                    && Objects.equal(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(filmId, title, description);
        }
    }
}
