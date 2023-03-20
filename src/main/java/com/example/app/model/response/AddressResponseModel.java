package com.example.app.model.response;

import com.example.app.model.mapping.serializer.NullToEmptyStringSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseModel extends RepresentationModel<AddressResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("addressId")
    @JacksonXmlProperty(localName = "addressId")
    private Integer addressId;

    @JsonProperty("address")
    @JacksonXmlProperty(localName = "address")
    private String address;

    @JsonProperty("address2")
    @JacksonXmlProperty(localName = "address2")
    @JsonSerialize(nullsUsing = NullToEmptyStringSerializer.class)
    private String address2;

    @JsonProperty("district")
    @JacksonXmlProperty(localName = "district")
    private String district;

    @JsonProperty("cityId")
    @JacksonXmlProperty(localName = "cityId")
    private Integer cityId;

    @JsonProperty("postalCode")
    @JacksonXmlProperty(localName = "postalCode")
    private String postalCode;

    @JsonProperty("phone")
    @JacksonXmlProperty(localName = "phone")
    private String phone;

    // @JsonProperty("location")
    // @JacksonXmlProperty(localName = "location")
    // private Point location;

    @JsonProperty("lastUpdate")
    @JacksonXmlProperty(localName = "lastUpdate")
    private LocalDateTime lastUpdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AddressResponseModel that = (AddressResponseModel) o;
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
        // return Objects.hashCode(super.hashCode(),
        //         addressId, address, address2, district, cityId, postalCode, phone, location, lastUpdate);
        return Objects.hashCode(super.hashCode(),
                addressId, address, address2, district, cityId, postalCode, phone, lastUpdate);
    }
}
