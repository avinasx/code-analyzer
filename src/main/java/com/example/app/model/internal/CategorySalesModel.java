package com.example.app.model.internal;

import com.example.app.model.enumeration.Category;
import com.google.common.base.Objects;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySalesModel {
    private Category category;
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
