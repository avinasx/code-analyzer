package com.example.app.services.catalog.domain.dto;

import com.example.app.common.constant.Category;
import com.example.app.common.constant.FilmRating;
import com.example.app.services.catalog.domain.converter.CategoryConverter;
import com.example.app.services.catalog.domain.converter.FilmRatingConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class FilmDetailsDto {
    /**
     * The film detail model provides a list of actors for each film.
     */
    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilmDetails {
        /**
         * film id
         */
        @JsonProperty(Fields.filmId)
        private Integer filmId;

        /**
         * film title
         */
        @JsonProperty(Fields.title)
        private String title;

        /**
         * film description
         */
        @JsonProperty(Fields.description)
        private String description;

        /**
         * category name
         */
        @JsonProperty(Fields.category)
        @Convert(converter = CategoryConverter.class)
        private Category category;

        /**
         * rental price
         */
        @JsonProperty(Fields.price)
        private BigDecimal price;

        /**
         * film length
         */
        @JsonProperty(Fields.length)
        private Integer length;

        /**
         * film rating
         */
        @JsonProperty(Fields.rating)
        @Convert(converter = FilmRatingConverter.class)
        private FilmRating rating;

        /**
         * film actors
         */
        @JsonProperty(Fields.actors)
        private String actors;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final FilmDetails that = (FilmDetails) o;
            return Objects.equal(filmId, that.filmId)
                    && Objects.equal(title, that.title)
                    && Objects.equal(description, that.description)
                    && category == that.category
                    && Objects.equal(price, that.price)
                    && Objects.equal(length, that.length)
                    && rating == that.rating
                    && Objects.equal(actors, that.actors);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(filmId, title, description, category, price, length, rating, actors);
        }
    }

    @Relation(collectionRelation = "filmDetailsList", itemRelation = "filmDetails")
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilmDetailsResponse extends RepresentationModel<FilmDetailsResponse> implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @JsonUnwrapped
        private FilmDetails filmDetails;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            final FilmDetailsResponse that = (FilmDetailsResponse) o;
            return Objects.equal(filmDetails, that.filmDetails);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(super.hashCode(), filmDetails);
        }
    }
}
