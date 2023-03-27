package com.example.app.model.internal.extra;

import com.example.app.model.constant.Category;
import com.example.app.model.constant.FilmRating;
import com.example.app.model.mapping.converter.CategoryConverter;
import com.example.app.model.mapping.converter.FilmRatingConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import lombok.*;

import java.math.BigDecimal;

/**
 * The film detail model provides a list of actors for each film.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDetailModel {
    /**
     * film id
     */
    @JsonProperty("filmId")
    private Integer filmId;

    /**
     * film title
     */
    @JsonProperty("title")
    private String title;

    /**
     * film description
     */
    @JsonProperty("description")
    private String description;

    /**
     * category name
     */
    @JsonProperty("category")
    @Convert(converter = CategoryConverter.class)
    private Category category;

    /**
     * rental price
     */
    @JsonProperty("price")
    private BigDecimal price;

    /**
     * film length
     */
    @JsonProperty("length")
    private Integer length;

    /**
     * film rating
     */
    @JsonProperty("rating")
    @Convert(converter = FilmRatingConverter.class)
    private FilmRating rating;

    /**
     * film actors
     */
    @JsonProperty("actors")
    private String actors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmDetailModel that = (FilmDetailModel) o;
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
