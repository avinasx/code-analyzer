package com.example.app.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.base.Objects;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("managerStaffId")
    @JacksonXmlProperty(localName = "managerStaffId")
    private Integer managerStaffId;

    @JsonProperty("addressId")
    @JacksonXmlProperty(localName = "addressId")
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
