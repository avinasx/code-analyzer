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
public class StaffRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("firstName")
    @JacksonXmlProperty(localName = "firstName")
    private String firstName;

    @JsonProperty("lastName")
    @JacksonXmlProperty(localName = "lastName")
    private String lastName;

    @JsonProperty("addressId")
    @JacksonXmlProperty(localName = "addressId")
    private Integer addressId;

    // @JsonProperty("picture")
    // @JacksonXmlProperty(localName = "picture")
    // private byte[] picture;

    @JsonProperty("email")
    @JacksonXmlProperty(localName = "email")
    private String email;

    @JsonProperty("storeId")
    @JacksonXmlProperty(localName = "storeId")
    private Integer storeId;

    @JsonProperty("active")
    @JacksonXmlProperty(localName = "active")
    private Boolean active;

    @JsonProperty("username")
    @JacksonXmlProperty(localName = "username")
    private String username;

    @JsonProperty("password")
    @JacksonXmlProperty(localName = "password")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffRequestModel that = (StaffRequestModel) o;
        return Objects.equal(firstName, that.firstName)
                && Objects.equal(lastName, that.lastName)
                && Objects.equal(addressId, that.addressId)
                // && Objects.equal(picture, that.picture)
                && Objects.equal(email, that.email)
                && Objects.equal(storeId, that.storeId)
                && Objects.equal(active, that.active)
                && Objects.equal(username, that.username)
                && Objects.equal(password, that.password);
    }

    @Override
    public int hashCode() {
        // return Objects.hashCode(firstName, lastName, addressId, picture, email, storeId, active, username, password);
        return Objects.hashCode(firstName, lastName, addressId, email, storeId, active, username, password);
    }
}
