package com.example.app.model.internal;

import com.example.app.model.enumeration.Category;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategorySalesModel {
    private Category category;
    private BigDecimal totalSales;
}
