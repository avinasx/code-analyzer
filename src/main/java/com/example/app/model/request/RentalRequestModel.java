package com.example.app.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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
}
