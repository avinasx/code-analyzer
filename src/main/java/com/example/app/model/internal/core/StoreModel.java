package com.example.app.model.internal.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
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
public class StoreModel {
    @JsonProperty("storeId")
    private Integer storeId;

    @JsonProperty("managerStaffId")
    private Integer managerStaffId;

    @JsonProperty("addressId")
    private Integer addressId;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("customersByStoreId")
    @ToString.Exclude
    private Collection<CustomerModel> customersByStoreId;

    @JsonIgnore
    @JsonProperty("inventoriesByStoreId")
    @ToString.Exclude
    private Collection<InventoryModel> inventoriesByStoreId;

    @JsonIgnore
    @JsonProperty("staffByStoreId")
    @ToString.Exclude
    private Collection<StaffModel> staffByStoreId;

    @JsonIgnore
    @JsonProperty("staffByManagerStaffId")
    @ToString.Exclude
    private StaffModel staffByManagerStaffId;

    @JsonIgnore
    @JsonProperty("addressByAddressId")
    @ToString.Exclude
    private AddressModel addressByAddressId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreModel that = (StoreModel) o;
        return Objects.equal(storeId, that.storeId)
                && Objects.equal(managerStaffId, that.managerStaffId)
                && Objects.equal(addressId, that.addressId)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(storeId, managerStaffId, addressId, lastUpdate);
    }
}
