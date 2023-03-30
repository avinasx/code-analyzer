package com.example.app.model.internal.core;

import com.example.app.model.constant.Category;
import com.example.app.model.mapping.converter.CategoryConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {
    @JsonProperty("categoryId")
    @Convert(converter = CategoryConverter.class)
    private Category categoryId;

    @JsonProperty("name")
    @Size(min = 1, max = 25)
    private String name;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("filmCategoriesByCategoryId")
    @ToString.Exclude
    private Collection<FilmCategoryModel> filmCategoriesByCategoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryModel that = (CategoryModel) o;
        return categoryId == that.categoryId
                && Objects.equal(name, that.name)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(categoryId, name, lastUpdate);
    }
}
