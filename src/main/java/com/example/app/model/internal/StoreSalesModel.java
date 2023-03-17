package com.example.app.model.internal;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreSalesModel {
    private String store;
    private String manager;
    private BigDecimal totalSales;
}
