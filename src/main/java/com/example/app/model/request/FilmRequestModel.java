package com.example.app.model.request;

import com.example.app.model.enumeration.FilmRating;
import com.example.app.model.enumeration.Language;
import com.example.app.model.enumeration.SpecialFeature;
import com.example.app.model.mapping.FilmRatingConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.Convert;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FilmRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("title")
    @JacksonXmlProperty(localName = "title")
    private String title;

    @JsonProperty("description")
    @JacksonXmlProperty(localName = "description")
    private String description;

    @JsonProperty("releaseYear")
    @JacksonXmlProperty(localName = "releaseYear")
    private LocalDate releaseYear;

    @JsonProperty("languageId")
    @JacksonXmlProperty(localName = "languageId")
    private Language languageId;

    @JsonProperty("originalLanguageId")
    @JacksonXmlProperty(localName = "originalLanguageId")
    private Language originalLanguageId;

    @JsonProperty("rentalDuration")
    @JacksonXmlProperty(localName = "rentalDuration")
    private Integer rentalDuration;

    @JsonProperty("rentalRate")
    @JacksonXmlProperty(localName = "rentalRate")
    private BigDecimal rentalRate;

    @JsonProperty("length")
    @JacksonXmlProperty(localName = "length")
    private Integer length;

    @JsonProperty("replacementCost")
    @JacksonXmlProperty(localName = "replacementCost")
    private BigDecimal replacementCost;

    @JsonProperty("rating")
    @JacksonXmlProperty(localName = "rating")
    @Convert(converter = FilmRatingConverter.class)
    private FilmRating rating;

    @JsonProperty("specialFeatures")
    @JacksonXmlProperty(localName = "specialFeatures")
    private Set<SpecialFeature> specialFeatures;
}
