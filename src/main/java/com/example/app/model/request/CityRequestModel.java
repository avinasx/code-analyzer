package com.example.app.model.request;

import com.example.app.model.enumeration.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CityRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("city")
    @JacksonXmlProperty(localName = "city")
    private String city;

    @JsonProperty("countryId")
    @JacksonXmlProperty(localName = "countryId")
    private Country countryId;
}
