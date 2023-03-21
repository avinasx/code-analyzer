package com.example.app.model.entity;

import com.example.app.model.enumeration.Country;
import com.example.app.model.mapping.converter.CountryConverter;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity(name = "country")
@Table(name = "country", schema = "sakila")
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "country_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Convert(converter = CountryConverter.class)
    private Country countryId;

    @Basic
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "countryByCountryId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<CityEntity> citiesByCountryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryEntity that = (CountryEntity) o;
        return countryId == that.countryId
                && Objects.equal(country, that.country)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(countryId, country, lastUpdate);
    }
}
