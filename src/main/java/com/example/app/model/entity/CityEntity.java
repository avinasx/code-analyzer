package com.example.app.model.entity;

import com.example.app.model.enumeration.Country;
import com.example.app.model.mapping.converter.CountryConverter;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity(name = "city")
@Table(name = "city", schema = "sakila", indexes = {
        @Index(name = "idx_fk_country_id", columnList = "country_id")
})
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CityEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "city_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cityId;

    @Basic
    @Column(name = "city", nullable = false, length = 50)
    @NonNull
    @Size(min = 1, max = 50)
    private String city;

    @Basic
    @Column(name = "country_id", nullable = false, insertable = false, updatable = false, columnDefinition = "SMALLINT UNSIGNED")
    @Convert(converter = CountryConverter.class)
    @NonNull
    private Country countryId;

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @NonNull
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "cityByCityId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<AddressEntity> addressesByCityId;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private CountryEntity countryByCountryId;

    public void update(CityEntity entity) {
        this.city = entity.city;
        this.countryId = entity.countryId;
        this.lastUpdate = entity.lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityEntity that = (CityEntity) o;
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
