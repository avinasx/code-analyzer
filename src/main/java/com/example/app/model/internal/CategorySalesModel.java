package com.example.app.model.internal;

import com.example.app.model.enumeration.Category;
import com.example.app.model.mapping.converter.CategoryConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import lombok.*;

import java.math.BigDecimal;

/**
 * The sales by category model provides the data for the total sales by film category.
 */
@Getter
@Setter
@ToString
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
