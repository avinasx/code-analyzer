package com.example.app.model.internal;

import com.example.app.model.entity.AddressEntity;
import com.example.app.model.entity.CustomerEntity;
import com.example.app.model.entity.InventoryEntity;
import com.example.app.model.entity.StaffEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
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
    private Collection<CustomerEntity> customersByStoreId;

    @JsonIgnore
    @JsonProperty("inventoriesByStoreId")
    private Collection<InventoryEntity> inventoriesByStoreId;

    @JsonIgnore
    @JsonProperty("staffByStoreId")
    private Collection<StaffEntity> staffByStoreId;

    @JsonIgnore
    @JsonProperty("staffByManagerStaffId")
    private StaffEntity staffByManagerStaffId;

    @JsonIgnore
    @JsonProperty("addressByAddressId")
    private AddressEntity addressByAddressId;

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
