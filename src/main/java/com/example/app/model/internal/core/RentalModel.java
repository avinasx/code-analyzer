package com.example.app.model.internal.core;

import com.example.app.model.entity.CustomerEntity;
import com.example.app.model.entity.InventoryEntity;
import com.example.app.model.entity.PaymentEntity;
import com.example.app.model.entity.StaffEntity;
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
public class RentalModel {
    @JsonProperty("rentalId")
    private Integer rentalId;

    @JsonProperty("rentalDate")
    private LocalDateTime rentalDate;

    @JsonProperty("inventoryId")
    private Integer inventoryId;

    @JsonProperty("customerId")
    private Integer customerId;

    @JsonProperty("returnDate")
    private LocalDateTime returnDate;

    @JsonProperty("staffId")
    private Integer staffId;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("paymentsByRentalId")
    private Collection<PaymentEntity> paymentsByRentalId;

    @JsonIgnore
    @JsonProperty("inventoryByInventoryId")
    private InventoryEntity inventoryByInventoryId;

    @JsonIgnore
    @JsonProperty("customerByCustomerId")
    private CustomerEntity customerByCustomerId;

    @JsonIgnore
    @JsonProperty("staffByStaffId")
    private StaffEntity staffByStaffId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalModel that = (RentalModel) o;
        return Objects.equal(rentalId, that.rentalId)
                && Objects.equal(rentalDate, that.rentalDate)
                && Objects.equal(inventoryId, that.inventoryId)
                && Objects.equal(customerId, that.customerId)
                && Objects.equal(returnDate, that.returnDate)
                && Objects.equal(staffId, that.staffId)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rentalId, rentalDate, inventoryId, customerId, returnDate,
                staffId, lastUpdate);
    }
}
