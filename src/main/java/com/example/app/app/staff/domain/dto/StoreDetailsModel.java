package com.example.app.app.staff.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@ToString
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailsModel {
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
        StoreDetailsModel that = (StoreDetailsModel) o;
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
