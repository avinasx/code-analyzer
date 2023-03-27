package com.example.app.model.internal.core;

import com.example.app.model.constant.Country;
import com.example.app.model.entity.AddressEntity;
import com.example.app.model.entity.CountryEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityModel {
    @JsonProperty("cityId")
    private Integer cityId;

    @JsonProperty("city")
    private String city;

    @JsonProperty("countryId")
    private Country countryId;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("addressesByCityId")
    private Collection<AddressEntity> addressesByCityId;

    @JsonIgnore
    @JsonProperty("countryByCountryId")
    private CountryEntity countryByCountryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityModel that = (CityModel) o;
        return Objects.equal(cityId, that.cityId)
                && Objects.equal(city, that.city)
                && countryId == that.countryId
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cityId, city, countryId, lastUpdate);
    }
}
