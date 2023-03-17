package com.example.app.model.entity;

import com.example.app.model.enumeration.Category;
import com.example.app.model.mapping.CategoryConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
public class FilmCategoryEntityPK implements Serializable {
    @Id
    @Column(name = "film_id", nullable = false, insertable = false, updatable = false, columnDefinition = "SMALLINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmId;
    @Id
    @Column(name = "category_id", nullable = false, insertable = false, updatable = false, columnDefinition = "TINYINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Convert(converter = CategoryConverter.class)
    private Category categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmCategoryEntityPK that = (FilmCategoryEntityPK) o;
        return Objects.equals(filmId, that.filmId)
                && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, categoryId);
    }
}
