package com.example.app.app.catalog.domain.dto;

import com.example.app.app.catalog.domain.converter.CategoryConverter;
import com.example.app.common.constant.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmCategoryModel {
    @JsonProperty("filmId")
    private Integer filmId;

    @JsonProperty("categoryId")
    @Convert(converter = CategoryConverter.class)
    private Category categoryId;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("filmByFilmId")
    @ToString.Exclude
    private FilmModel filmByFilmId;

    @JsonIgnore
    @JsonProperty("categoryByCategoryId")
    @ToString.Exclude
    private CategoryModel categoryByCategoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmCategoryModel that = (FilmCategoryModel) o;
        return Objects.equal(filmId, that.filmId)
                && categoryId == that.categoryId
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(filmId, categoryId, lastUpdate);
    }
}
