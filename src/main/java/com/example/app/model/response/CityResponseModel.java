package com.example.app.model.response;

import com.example.app.model.enumeration.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CityResponseModel that = (CityResponseModel) o;
        return Objects.equal(cityId, that.cityId)
                && Objects.equal(city, that.city)
                && countryId == that.countryId
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), cityId, city, countryId, lastUpdate);
    }
}
