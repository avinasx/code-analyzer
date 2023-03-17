package com.example.app.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("customerId")
    @JacksonXmlProperty(localName = "customerId")
    private Integer customerId;

    @JsonProperty("staffId")
    @JacksonXmlProperty(localName = "staffId")
    private Integer staffId;

    @JsonProperty("rentalId")
    @JacksonXmlProperty(localName = "rentalId")
    private Integer rentalId;

    @JsonProperty("amount")
    @JacksonXmlProperty(localName = "amount")
    private BigDecimal amount;

    @JsonProperty("paymentDate")
    @JacksonXmlProperty(localName = "paymentDate")
    private LocalDateTime paymentDate;
}
