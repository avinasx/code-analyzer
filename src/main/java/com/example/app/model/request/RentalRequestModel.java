package com.example.app.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.base.Objects;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("rentalDate")
    @JacksonXmlProperty(localName = "rentalDate")
    private LocalDateTime rentalDate;

    @JsonProperty("inventoryId")
    @JacksonXmlProperty(localName = "inventoryId")
    private Integer inventoryId;

    @JsonProperty("customerId")
    @JacksonXmlProperty(localName = "customerId")
    private Integer customerId;

    @JsonProperty("returnDate")
    @JacksonXmlProperty(localName = "returnDate")
    private LocalDateTime returnDate;

    @JsonProperty("staffId")
    @JacksonXmlProperty(localName = "staffId")
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
