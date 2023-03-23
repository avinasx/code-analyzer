package com.example.app.model.entity;

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

@Entity(name = "address")
@Table(name = "address", schema = "sakila", indexes = {
        @Index(name = "idx_fk_city_id", columnList = "city_id")
        // @Index(name = "idx_location", columnList = "location")
})
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddressEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "address_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @Basic
    @Column(name = "address", nullable = false, length = 50)
    @NonNull
    @Size(min = 1, max = 50)
    private String address;

    @Basic
    @Column(name = "address2", nullable = true, length = 50)
    @Size(min = 1, max = 50)
    private String address2;

    @Basic
    @Column(name = "district", nullable = false, length = 20)
    @NonNull
    @Size(min = 1, max = 20)
    private String district;

    @Basic
    @Column(name = "city_id", nullable = false, insertable = false, updatable = false, columnDefinition = "SMALLINT UNSIGNED")
    @NonNull
    private Integer cityId;

    @Basic
    @Column(name = "postal_code", nullable = true, length = 10)
    @Size(min = 1, max = 10)
    private String postalCode;

    @Basic
    @Column(name = "phone", nullable = false, length = 20)
    @NonNull
    @Size(min = 1, max = 20)
    private String phone;

    // @Basic
    // @Column(name = "location", nullable = false, columnDefinition = "GEOMETRY SRID 0")
    // @Convert(converter = GeometryConverter.class)
    // @NonNull
    // private Point location;

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @NonNull
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "city_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private CityEntity cityByCityId;

    @OneToMany(mappedBy = "addressByAddressId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<CustomerEntity> customersByAddressId;

    @OneToMany(mappedBy = "addressByAddressId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<StaffEntity> staffByAddressId;

    @OneToMany(mappedBy = "addressByAddressId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<StoreEntity> storesByAddressId;

    public void update(AddressEntity entity) {
        this.address = entity.address;
        this.address2 = entity.address2;
        this.district = entity.district;
        this.cityId = entity.cityId;
        this.postalCode = entity.postalCode;
        this.phone = entity.phone;
        // this.location = entity.location;
        this.lastUpdate = entity.lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return Objects.equal(addressId, that.addressId)
                && Objects.equal(address, that.address)
                && Objects.equal(address2, that.address2)
                && Objects.equal(district, that.district)
                && Objects.equal(cityId, that.cityId)
                && Objects.equal(postalCode, that.postalCode)
                && Objects.equal(phone, that.phone)
                // && Objects.equal(location, that.location)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        // return Objects.hashCode(addressId, address, address2, district, cityId, postalCode, phone,
        //         location, lastUpdate);
        return Objects.hashCode(addressId, address, address2, district, cityId, postalCode, phone, lastUpdate);
    }
}
