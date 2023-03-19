package com.example.app.model.internal;

import com.google.common.base.Objects;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSalesModel {
    private String store;
    private String manager;
    private BigDecimal totalSales;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreSalesModel that = (StoreSalesModel) o;
        return Objects.equal(store, that.store)
                && Objects.equal(manager, that.manager)
                && Objects.equal(totalSales, that.totalSales);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(store, manager, totalSales);
    }
}
