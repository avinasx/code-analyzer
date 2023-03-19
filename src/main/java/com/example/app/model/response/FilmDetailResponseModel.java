package com.example.app.model.response;

import com.example.app.model.enumeration.Category;
import com.example.app.model.enumeration.FilmRating;
import com.example.app.model.mapping.CategoryConverter;
import com.example.app.model.mapping.FilmRatingConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
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
public class FilmDetailResponseModel extends RepresentationModel<FilmDetailResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * film id
     */
    @JsonProperty("fid")
    @JacksonXmlProperty(localName = "fid")
    private Integer fid;

    /**
     * film title
     */
    @JsonProperty("title")
    @JacksonXmlProperty(localName = "title")
    private String title;

    /**
     * film description
     */
    @JsonProperty("description")
    @JacksonXmlProperty(localName = "description")
    private String description;

    /**
     * category name
     */
    @JsonProperty("category")
    @JacksonXmlProperty(localName = "category")
    @Convert(converter = CategoryConverter.class)
    private Category category;

    /**
     * rental price
     */
    @JsonProperty("price")
    @JacksonXmlProperty(localName = "price")
    private BigDecimal price;

    /**
     * film length
     */
    @JsonProperty("length")
    @JacksonXmlProperty(localName = "length")
    private Integer length;

    /**
     * film rating
     */
    @JsonProperty("rating")
    @JacksonXmlProperty(localName = "rating")
    @Convert(converter = FilmRatingConverter.class)
    private FilmRating rating;

    /**
     * film actors
     */
    @JsonProperty("actors")
    @JacksonXmlProperty(localName = "actors")
    private String actors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FilmDetailResponseModel that = (FilmDetailResponseModel) o;
        return Objects.equal(fid, that.fid)
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
        return Objects.hashCode(super.hashCode(), fid, title, description, category, price, length, rating, actors);
    }
}
