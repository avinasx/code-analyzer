package com.example.app.model.internal;

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
public class CustomerModel {
    @JsonProperty("customerId")
    private Integer customerId;

    @JsonProperty("storeId")
    private Integer storeId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
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
    private StoreEntity storeByStoreId;

    @JsonIgnore
    @JsonProperty("addressByAddressId")
    private AddressEntity addressByAddressId;

    @JsonIgnore
    @JsonProperty("paymentsByCustomerId")
    private Collection<PaymentEntity> paymentsByCustomerId;

    @JsonIgnore
    @JsonProperty("rentalsByCustomerId")
    private Collection<RentalEntity> rentalsByCustomerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerModel that = (CustomerModel) o;
        return Objects.equal(customerId, that.customerId)
                && Objects.equal(storeId, that.storeId)
                && Objects.equal(firstName, that.firstName)
                && Objects.equal(lastName, that.lastName)
                && Objects.equal(email, that.email)
                && Objects.equal(addressId, that.addressId)
                && Objects.equal(active, that.active)
                && Objects.equal(createDate, that.createDate)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customerId, storeId, firstName, lastName, email,
                addressId, active, createDate, lastUpdate);
    }
}
