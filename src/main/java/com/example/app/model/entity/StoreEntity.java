package com.example.app.model.entity;

import com.google.common.base.Objects;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity(name = "store")
@Table(name = "store", schema = "sakila", indexes = {
        @Index(name = "idx_unique_manager", columnList = "manager_staff_id", unique = true),
        @Index(name = "idx_fk_address_id", columnList = "address_id")
})
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "store_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;

    @Basic
    @Column(name = "manager_staff_id", nullable = false, insertable = false, updatable = false, columnDefinition = "TINYINT UNSIGNED")
    @NonNull
    private Integer managerStaffId;

    @Basic
    @Column(name = "address_id", nullable = false, insertable = false, updatable = false, columnDefinition = "SMALLINT UNSIGNED")
    @NonNull
    private Integer addressId;

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @NonNull
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "storeByStoreId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<CustomerEntity> customersByStoreId;

    @OneToMany(mappedBy = "storeByStoreId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<InventoryEntity> inventoriesByStoreId;

    @OneToMany(mappedBy = "storeByStoreId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<StaffEntity> staffByStoreId;

    @ManyToOne
    @JoinColumn(name = "manager_staff_id", referencedColumnName = "staff_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private StaffEntity staffByManagerStaffId;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private AddressEntity addressByAddressId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreEntity that = (StoreEntity) o;
        return Objects.equal(storeId, that.storeId)
                && Objects.equal(managerStaffId, that.managerStaffId)
                && Objects.equal(addressId, that.addressId)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(storeId, managerStaffId, addressId, lastUpdate);
    }
}
