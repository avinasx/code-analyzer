package com.example.app.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

/**
 * The sales by store model provides the data for the total sales by store.
 */
@Relation(collectionRelation = "storeSales", itemRelation = "storeSales")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSalesModel {
    /**
     * store
     */
    @JsonProperty("store")
    private String store;

    /**
     * store manager
     */
    @JsonProperty("manager")
    private String manager;

    /**
     * total sales
     */
    @JsonProperty("totalSales")
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
