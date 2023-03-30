package com.example.app.model.internal.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
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
public class StaffModel {
    @JsonProperty("staffId")
    private Integer staffId;

    @JsonUnwrapped
    private FullName fullName;

    @JsonProperty("addressId")
    private Integer addressId;

    // @JsonProperty("picture")
    // private byte[] picture;

    @JsonProperty("email")
    @Size(min = 1, max = 50)
    private String email;

    @JsonProperty("storeId")
    private Integer storeId;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("username")
    @Size(min = 1, max = 16)
    private String username;

    @JsonProperty("password")
    @Size(min = 1, max = 40)
    private String password;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("paymentsByStaffId")
    @ToString.Exclude
    private Collection<PaymentModel> paymentsByStaffId;

    @JsonIgnore
    @JsonProperty("rentalsByStaffId")
    @ToString.Exclude
    private Collection<RentalModel> rentalsByStaffId;

    @JsonIgnore
    @JsonProperty("addressByAddressId")
    @ToString.Exclude
    private AddressModel addressByAddressId;

    @JsonIgnore
    @JsonProperty("storeByStoreId")
    @ToString.Exclude
    private StoreModel storeByStoreId;

    @JsonIgnore
    @JsonProperty("storesByStaffId")
    @ToString.Exclude
    private Collection<StoreModel> storesByStaffId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffModel that = (StaffModel) o;
        return Objects.equal(staffId, that.staffId)
                && Objects.equal(fullName, that.fullName)
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
        // return Objects.hashCode(staffId, fullName, addressId, picture, email,
        //         storeId, active, username, password, lastUpdate);
        return Objects.hashCode(staffId, fullName, addressId, email,
                storeId, active, username, password, lastUpdate);
    }
}
