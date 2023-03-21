package com.example.app.model.internal;

import com.example.app.model.entity.CityEntity;
import com.example.app.model.entity.CustomerEntity;
import com.example.app.model.entity.StaffEntity;
import com.example.app.model.entity.StoreEntity;
import com.example.app.model.mapping.serializer.NullToEmptyStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class AddressModel {
    @JsonProperty("addressId")
    private Integer addressId;

    @JsonProperty("address")
    private String address;

    @JsonProperty("address2")
    @JsonSerialize(nullsUsing = NullToEmptyStringSerializer.class)
    private String address2;

    @JsonProperty("district")
    private String district;

    @JsonProperty("cityId")
    private Integer cityId;

    @JsonProperty("postalCode")
    private String postalCode;

    @JsonProperty("phone")
    private String phone;

    // @JsonProperty("location")
    // private Point location;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("cityByCityId")
    private CityEntity cityByCityId;

    @JsonIgnore
    @JsonProperty("customersByAddressId")
    private Collection<CustomerEntity> customersByAddressId;

    @JsonIgnore
    @JsonProperty("staffByAddressId")
    private Collection<StaffEntity> staffByAddressId;

    @JsonIgnore
    @JsonProperty("storesByAddressId")
    private Collection<StoreEntity> storesByAddressId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressModel that = (AddressModel) o;
        return Objects.equal(addressId, that.addressId)
                && Objects.equal(address, that.address)
                && Objects.equal(address2, that.address2)
                && Objects.equal(district, that.district)
                && Objects.equal(cityId, that.cityId)
                && Objects.equal(postalCode, that.postalCode)
                && Objects.equal(phone, that.phone)
                // && Objects.equal(location, that.location)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        // return Objects.hashCode(addressId, address, address2, district, cityId, postalCode, phone, location, lastUpdate);
        return Objects.hashCode(addressId, address, address2, district, cityId, postalCode, phone, lastUpdate);
    }
}
