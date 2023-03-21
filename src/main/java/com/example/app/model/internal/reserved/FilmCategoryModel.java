package com.example.app.model.internal.reserved;

import com.example.app.model.enumeration.Category;
import com.example.app.model.mapping.converter.CategoryConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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
