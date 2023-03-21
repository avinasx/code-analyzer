package com.example.app.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailModel {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("store")
    private String store;

    @JsonProperty("manager")
    private String manager;

    @JsonProperty("address")
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreDetailModel that = (StoreDetailModel) o;
        return Objects.equal(id, that.id)
                && Objects.equal(store, that.store)
                && Objects.equal(manager, that.manager)
                && Objects.equal(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, store, manager, address);
    }
}
