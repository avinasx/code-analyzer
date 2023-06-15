package com.example.app.app.location.domain.dto;

import com.example.app.app.location.domain.converter.CountryConverter;
import com.example.app.common.constant.Country;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.persistence.Convert;
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
public class CountryModel {
    @JsonProperty("countryId")
    private Country countryId;

    @JsonProperty("country")
    @Convert(converter = CountryConverter.class)
    @Size(min = 1, max = 50)
    private Country country;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("citiesByCountryId")
    @ToString.Exclude
    private Collection<CityModel> citiesByCountryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryModel that = (CountryModel) o;
        return countryId == that.countryId
                && country == that.country
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(countryId, country, lastUpdate);
    }
}
