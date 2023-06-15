package com.example.app.app.catalog.domain.dto;

import com.example.app.app.catalog.domain.converter.CategoryConverter;
import com.example.app.common.constant.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

/**
 * The sales by category model provides the data for the total sales by film category.
 */
@Getter
@Setter
@ToString
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySalesModel {
    /**
     * category
     */
    @JsonProperty("category")
    @Convert(converter = CategoryConverter.class)
    private Category category;

    /**
     * total sales
     */
    @JsonProperty("totalSales")
    private BigDecimal totalSales;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategorySalesModel that = (CategorySalesModel) o;
        return category == that.category
                && Objects.equal(totalSales, that.totalSales);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(category, totalSales);
    }
}
