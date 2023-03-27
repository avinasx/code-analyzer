package com.example.app.model.internal.core;

import com.example.app.model.entity.CustomerEntity;
import com.example.app.model.entity.RentalEntity;
import com.example.app.model.entity.StaffEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentModel {
    @JsonProperty("paymentId")
    private Integer paymentId;

    @JsonProperty("customerId")
    private Integer customerId;

    @JsonProperty("staffId")
    private Integer staffId;

    @JsonProperty("rentalId")
    private Integer rentalId;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("paymentDate")
    private LocalDateTime paymentDate;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("customerByCustomerId")
    private CustomerEntity customerByCustomerId;

    @JsonIgnore
    @JsonProperty("staffByStaffId")
    private StaffEntity staffByStaffId;

    @JsonIgnore
    @JsonProperty("rentalByRentalId")
    private RentalEntity rentalByRentalId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentModel that = (PaymentModel) o;
        return Objects.equal(paymentId, that.paymentId)
                && Objects.equal(customerId, that.customerId)
                && Objects.equal(staffId, that.staffId)
                && Objects.equal(rentalId, that.rentalId)
                && Objects.equal(amount, that.amount)
                && Objects.equal(paymentDate, that.paymentDate)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(paymentId, customerId, staffId, rentalId, amount,
                paymentDate, lastUpdate);
    }
}
