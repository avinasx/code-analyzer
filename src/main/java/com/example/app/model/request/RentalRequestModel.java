package com.example.app.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalRequestModel that = (RentalRequestModel) o;
        return Objects.equal(rentalDate, that.rentalDate)
                && Objects.equal(inventoryId, that.inventoryId)
                && Objects.equal(customerId, that.customerId)
                && Objects.equal(returnDate, that.returnDate)
                && Objects.equal(staffId, that.staffId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rentalDate, inventoryId, customerId, returnDate, staffId);
    }
}
