package com.example.app.model.internal.core;

import com.example.app.model.entity.AddressEntity;
import com.example.app.model.entity.PaymentEntity;
import com.example.app.model.entity.RentalEntity;
import com.example.app.model.entity.StoreEntity;
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
public class StaffModel {
    @JsonProperty("staffId")
    private Integer staffId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("addressId")
    private Integer addressId;

    // @JsonProperty("picture")
    // private byte[] picture;

    @JsonProperty("email")
    private String email;

    @JsonProperty("storeId")
    private Integer storeId;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("paymentsByStaffId")
    private Collection<PaymentEntity> paymentsByStaffId;

    @JsonIgnore
    @JsonProperty("rentalsByStaffId")
    private Collection<RentalEntity> rentalsByStaffId;

    @JsonIgnore
    @JsonProperty("addressByAddressId")
    private AddressEntity addressByAddressId;

    @JsonIgnore
    @JsonProperty("storeByStoreId")
    private StoreEntity storeByStoreId;

    @JsonIgnore
    @JsonProperty("storesByStaffId")
    private Collection<StoreEntity> storesByStaffId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffModel that = (StaffModel) o;
        return Objects.equal(staffId, that.staffId)
                && Objects.equal(firstName, that.firstName)
                && Objects.equal(lastName, that.lastName)
                && Objects.equal(addressId, that.addressId)
                // && Objects.equal(picture, that.picture)
                && Objects.equal(email, that.email)
                && Objects.equal(storeId, that.storeId)
                && Objects.equal(active, that.active)
                && Objects.equal(username, that.username)
                && Objects.equal(password, that.password)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        // return Objects.hashCode(staffId, firstName, lastName, addressId, picture, email,
        //         storeId, active, username, password, lastUpdate);
        return Objects.hashCode(staffId, firstName, lastName, addressId, email,
                storeId, active, username, password, lastUpdate);
    }
}
