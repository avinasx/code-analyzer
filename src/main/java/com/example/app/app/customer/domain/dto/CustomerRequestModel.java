package com.example.app.app.customer.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.validation.constraints.Size;
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
public class CustomerRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("storeId")
    private Integer storeId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    @Size(min = 1, max = 50)
    private String email;

    @JsonProperty("addressId")
    private Integer addressId;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("createDate")
    private LocalDateTime createDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerRequestModel that = (CustomerRequestModel) o;
        return Objects.equal(storeId, that.storeId)
                && Objects.equal(firstName, that.firstName)
                && Objects.equal(lastName, that.lastName)
                && Objects.equal(email, that.email)
                && Objects.equal(addressId, that.addressId)
                && Objects.equal(active, that.active)
                && Objects.equal(createDate, that.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(storeId, firstName, lastName, email, addressId, active, createDate);
    }
}
