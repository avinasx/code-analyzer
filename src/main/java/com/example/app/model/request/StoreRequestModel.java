package com.example.app.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("managerStaffId")
    private Integer managerStaffId;

    @JsonProperty("addressId")
    private Integer addressId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreRequestModel that = (StoreRequestModel) o;
        return Objects.equal(managerStaffId, that.managerStaffId)
                && Objects.equal(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(managerStaffId, addressId);
    }
}
