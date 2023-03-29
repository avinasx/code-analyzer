package com.example.app.model.request;

import com.example.app.model.constant.FilmRating;
import com.example.app.model.constant.Language;
import com.example.app.model.constant.SpecialFeature;
import com.example.app.model.mapping.converter.FilmRatingConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("title")
    @Size(min = 1, max = 128)
    private String title;

    @JsonProperty("description")
    @Size(max = 65535)
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
    private EnumSet<SpecialFeature> specialFeatures;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmRequestModel that = (FilmRequestModel) o;
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
