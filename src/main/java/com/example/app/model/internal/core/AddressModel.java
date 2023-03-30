package com.example.app.model.internal.core;

import com.example.app.model.mapping.serializer.NullToEmptyStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;
import jakarta.validation.constraints.Size;
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
public class AddressModel {
    @JsonProperty("addressId")
    private Integer addressId;

    @JsonProperty("address")
    @Size(min = 1, max = 50)
    private String address;

    @JsonProperty("address2")
    @JsonSerialize(nullsUsing = NullToEmptyStringSerializer.class)
    @Size(min = 1, max = 50)
    private String address2;

    @JsonProperty("district")
    @Size(min = 1, max = 20)
    private String district;

    @JsonProperty("cityId")
    private Integer cityId;

    @JsonProperty("postalCode")
    @Size(min = 1, max = 10)
    private String postalCode;

    @JsonProperty("phone")
    @Size(min = 1, max = 20)
    private String phone;

    // @JsonProperty("location")
    // private Point location;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("cityByCityId")
    @ToString.Exclude
    private CityModel cityByCityId;

    @JsonIgnore
    @JsonProperty("customersByAddressId")
    @ToString.Exclude
    private Collection<CustomerModel> customersByAddressId;

    @JsonIgnore
    @JsonProperty("staffByAddressId")
    @ToString.Exclude
    private Collection<StaffModel> staffByAddressId;

    @JsonIgnore
    @JsonProperty("storesByAddressId")
    @ToString.Exclude
    private Collection<StoreModel> storesByAddressId;

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
