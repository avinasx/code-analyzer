package com.example.app.model.internal;

import com.example.app.model.enumeration.Category;
import com.example.app.model.enumeration.FilmRating;
import com.google.common.base.Objects;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDetailModel {
    private Integer fid;
    private String title;
    private String description;
    private Category category;
    private BigDecimal price;
    private Integer length;
    private FilmRating rating;
    private String actors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmDetailModel that = (FilmDetailModel) o;
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
        return Objects.hashCode(fid, title, description, category, price, length, rating, actors);
    }
}
