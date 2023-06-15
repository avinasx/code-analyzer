package com.example.app.app.staff.domain.dto;

import com.example.app.app.location.domain.converter.CountryConverter;
import com.example.app.common.constant.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import lombok.*;
import lombok.experimental.FieldNameConstants;

/**
 * The staff detail model provides address and store information for staff members.
 */
@Getter
@Setter
@ToString
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffDetailsModel {
    /**
     * staff id
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * staff name (first + last)
     */
    @JsonProperty("name")
    private String name;

    /**
     * address
     */
    @JsonProperty("address")
    private String address;

    /**
     * postal code
     */
    @JsonProperty("zipCode")
    private String zipCode;

    /**
     * phone number
     */
    @JsonProperty("phone")
    private String phone;

    /**
     * city
     */
    @JsonProperty("city")
    private String city;

    /**
     * country
     */
    @JsonProperty("country")
    @Convert(converter = CountryConverter.class)
    private Country country;

    /**
     * store id
     */
    @JsonProperty("sid")
    private Integer sid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffDetailsModel that = (StaffDetailsModel) o;
        return Objects.equal(id, that.id)
                && Objects.equal(name, that.name)
                && Objects.equal(address, that.address)
                && Objects.equal(zipCode, that.zipCode)
                && Objects.equal(phone, that.phone)
                && Objects.equal(city, that.city)
                && country == that.country
                && Objects.equal(sid, that.sid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, address, zipCode, phone, city, country, sid);
    }
}
