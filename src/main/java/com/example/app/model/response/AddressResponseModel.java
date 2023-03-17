package com.example.app.model.response;

import com.example.app.model.mapping.NullToEmptyStringSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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

    @JsonSerialize(nullsUsing = NullToEmptyStringSerializer.class)
    @JsonProperty("address2")
    @JacksonXmlProperty(localName = "address2")
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
}
