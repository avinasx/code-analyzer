package com.example.app.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseModel extends RepresentationModel<CustomerResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("customerId")
    @JacksonXmlProperty(localName = "customerId")
    private Integer customerId;

    @JsonProperty("storeId")
    @JacksonXmlProperty(localName = "storeId")
    private Integer storeId;

    @JsonProperty("firstName")
    @JacksonXmlProperty(localName = "firstName")
    private String firstName;

    @JsonProperty("lastName")
    @JacksonXmlProperty(localName = "lastName")
    private String lastName;

    @JsonProperty("email")
    @JacksonXmlProperty(localName = "email")
    private String email;

    @JsonProperty("addressId")
    @JacksonXmlProperty(localName = "addressId")
    private Integer addressId;

    @JsonProperty("active")
    @JacksonXmlProperty(localName = "active")
    private Boolean active;

    @JsonProperty("createDate")
    @JacksonXmlProperty(localName = "createDate")
    private LocalDateTime createDate;

    @JsonProperty("lastUpdate")
    @JacksonXmlProperty(localName = "lastUpdate")
    private LocalDateTime lastUpdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomerResponseModel that = (CustomerResponseModel) o;
        return Objects.equal(customerId, that.customerId)
                && Objects.equal(storeId, that.storeId)
                && Objects.equal(firstName, that.firstName)
                && Objects.equal(lastName, that.lastName)
                && Objects.equal(email, that.email)
                && Objects.equal(addressId, that.addressId)
                && Objects.equal(active, that.active)
                && Objects.equal(createDate, that.createDate)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), customerId, storeId, firstName, lastName, email,
                addressId, active, createDate, lastUpdate);
    }
}
