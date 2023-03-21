package com.example.app.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentRequestModel that = (PaymentRequestModel) o;
        return Objects.equal(customerId, that.customerId)
                && Objects.equal(staffId, that.staffId)
                && Objects.equal(rentalId, that.rentalId)
                && Objects.equal(amount, that.amount)
                && Objects.equal(paymentDate, that.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customerId, staffId, rentalId, amount, paymentDate);
    }
}
