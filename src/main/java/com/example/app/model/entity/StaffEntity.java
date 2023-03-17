package com.example.app.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "staff")
@Table(name = "staff", schema = "sakila")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "staff_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staffId;

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Basic
    @Column(name = "address_id", nullable = false, insertable = false, updatable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Integer addressId;

    // @Basic
    // @Column(name = "picture", nullable = true, columnDefinition = "BLOB")
    // @ColumnDefault("NULL")
    // @Lob
    // private byte[] picture;

    @Basic
    @Column(name = "email", nullable = true, length = 50)
    @ColumnDefault("NULL")
    private String email;

    @Basic
    @Column(name = "store_id", nullable = false, insertable = false, updatable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer storeId;

    @Basic
    @Column(name = "active", nullable = false, columnDefinition = "BOOLEAN")
    @ColumnDefault("TRUE")
    private Boolean active;

    @Basic
    @Column(name = "username", nullable = false, length = 16)
    private String username;

    @Basic
    @Column(name = "password", nullable = true, length = 40, columnDefinition = "VARCHAR(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin")
    @ColumnDefault("NULL")
    private String password;

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "staffByStaffId", cascade = CascadeType.ALL)
    private Collection<PaymentEntity> paymentsByStaffId;

    @OneToMany(mappedBy = "staffByStaffId", cascade = CascadeType.ALL)
    private Collection<RentalEntity> rentalsByStaffId;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    private AddressEntity addressByAddressId;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    private StoreEntity storeByStoreId;

    @OneToMany(mappedBy = "staffByManagerStaffId", cascade = CascadeType.ALL)
    private Collection<StoreEntity> storesByStaffId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffEntity that = (StaffEntity) o;
        return Objects.equals(staffId, that.staffId)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(addressId, that.addressId)
                // && Arrays.equals(picture, that.picture)
                && Objects.equals(email, that.email)
                && Objects.equals(storeId, that.storeId)
                && Objects.equals(active, that.active)
                && Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(staffId, firstName, lastName, addressId, email,
                storeId, active, username, password, lastUpdate);
        // result = 31 * result + Arrays.hashCode(picture);
        return result;
    }
}
