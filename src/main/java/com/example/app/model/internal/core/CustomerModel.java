package com.example.app.model.internal.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {
    @JsonProperty("customerId")
    private Integer customerId;

    @JsonProperty("storeId")
    private Integer storeId;

    @JsonUnwrapped
    private FullName fullName;

    @JsonProperty("email")
    @Size(min = 1, max = 50)
    private String email;

    @JsonProperty("addressId")
    private Integer addressId;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("storeByStoreId")
    @ToString.Exclude
    private StoreModel storeByStoreId;

    @JsonIgnore
    @JsonProperty("addressByAddressId")
    @ToString.Exclude
    private AddressModel addressByAddressId;

    @JsonIgnore
    @JsonProperty("paymentsByCustomerId")
    @ToString.Exclude
    private Collection<PaymentModel> paymentsByCustomerId;

    @JsonIgnore
    @JsonProperty("rentalsByCustomerId")
    @ToString.Exclude
    private Collection<RentalModel> rentalsByCustomerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerModel that = (CustomerModel) o;
        return Objects.equal(customerId, that.customerId)
                && Objects.equal(storeId, that.storeId)
                && Objects.equal(fullName, that.fullName)
                && Objects.equal(email, that.email)
                && Objects.equal(addressId, that.addressId)
                && Objects.equal(active, that.active)
                && Objects.equal(createDate, that.createDate)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customerId, storeId, fullName, email,
                addressId, active, createDate, lastUpdate);
    }
}
