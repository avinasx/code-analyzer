package com.example.app.model.response.reserved;

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
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponseModel extends RepresentationModel<CountryResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("countryId")
    @JacksonXmlProperty(localName = "countryId")
    private Country countryId;

    @JsonProperty("country")
    @JacksonXmlProperty(localName = "country")
    @Convert(converter = CountryConverter.class)
    private Country country;

    @JsonProperty("lastUpdate")
    @JacksonXmlProperty(localName = "lastUpdate")
    private LocalDateTime lastUpdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CountryResponseModel that = (CountryResponseModel) o;
        return countryId == that.countryId
                && country == that.country
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), countryId, country, lastUpdate);
    }
}
