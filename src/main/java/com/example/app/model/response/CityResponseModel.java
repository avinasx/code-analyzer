package com.example.app.model.response;

import com.example.app.model.enumeration.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CityResponseModel extends RepresentationModel<CityResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("cityId")
    @JacksonXmlProperty(localName = "cityId")
    private Integer cityId;

    @JsonProperty("city")
    @JacksonXmlProperty(localName = "city")
    private String city;

    @JsonProperty("countryId")
    @JacksonXmlProperty(localName = "countryId")
    private Country countryId;

    @JsonProperty("lastUpdate")
    @JacksonXmlProperty(localName = "lastUpdate")
    private LocalDateTime lastUpdate;
}
