package com.example.app.model.response;

import com.example.app.model.enumeration.Country;
import com.example.app.model.mapping.converter.CountryConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

/**
 * The customer detail model provides a list of customers, with name and address.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailResponseModel extends RepresentationModel<CustomerDetailResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * customer id
     */
    @JsonProperty("id")
    @JacksonXmlProperty(localName = "id")
    private Integer id;

    /**
     * customer name (first + last)
     */
    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    private String name;

    /**
     * address
     */
    @JsonProperty("address")
    @JacksonXmlProperty(localName = "address")
    private String address;

    /**
     * postal code
     */
    @JsonProperty("zipCode")
    @JacksonXmlProperty(localName = "zipCode")
    private String zipCode;

    /**
     * phone number
     */
    @JsonProperty("phone")
    @JacksonXmlProperty(localName = "phone")
    private String phone;

    /**
     * city
     */
    @JsonProperty("city")
    @JacksonXmlProperty(localName = "city")
    private String city;

    /**
     * country
     */
    @JsonProperty("country")
    @JacksonXmlProperty(localName = "country")
    @Convert(converter = CountryConverter.class)
    private Country country;

    /**
     * notes (whether the customer is active)
     */
    @JsonProperty("notes")
    @JacksonXmlProperty(localName = "notes")
    private String notes;

    /**
     * store id
     */
    @JsonProperty("sid")
    @JacksonXmlProperty(localName = "sid")
    private Integer sid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomerDetailResponseModel that = (CustomerDetailResponseModel) o;
        return Objects.equal(id, that.id)
                && Objects.equal(name, that.name)
                && Objects.equal(address, that.address)
                && Objects.equal(zipCode, that.zipCode)
                && Objects.equal(phone, that.phone)
                && Objects.equal(city, that.city)
                && country == that.country
                && Objects.equal(notes, that.notes)
                && Objects.equal(sid, that.sid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, name, address, zipCode, phone, city, country, notes, sid);
    }
}
