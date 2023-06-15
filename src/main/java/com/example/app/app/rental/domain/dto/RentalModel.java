package com.example.app.app.rental.domain.dto;

import com.example.app.app.customer.domain.dto.CustomerModel;
import com.example.app.app.payment.domain.dto.PaymentModel;
import com.example.app.app.staff.domain.dto.StaffModel;
import com.example.app.app.store.domain.dto.InventoryModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
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
    @ToString.Exclude
    private Collection<PaymentModel> paymentsByRentalId;

    @JsonIgnore
    @JsonProperty("inventoryByInventoryId")
    @ToString.Exclude
    private InventoryModel inventoryByInventoryId;

    @JsonIgnore
    @JsonProperty("customerByCustomerId")
    @ToString.Exclude
    private CustomerModel customerByCustomerId;

    @JsonIgnore
    @JsonProperty("staffByStaffId")
    @ToString.Exclude
    private StaffModel staffByStaffId;

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
