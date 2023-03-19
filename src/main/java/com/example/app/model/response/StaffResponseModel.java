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
public class StaffResponseModel extends RepresentationModel<StaffResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("staffId")
    @JacksonXmlProperty(localName = "staffId")
    private Integer staffId;

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

    @JsonProperty("lastUpdate")
    @JacksonXmlProperty(localName = "lastUpdate")
    private LocalDateTime lastUpdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StaffResponseModel that = (StaffResponseModel) o;
        return Objects.equal(staffId, that.staffId)
                && Objects.equal(firstName, that.firstName)
                && Objects.equal(lastName, that.lastName)
                && Objects.equal(addressId, that.addressId)
                // && Objects.equal(picture, that.picture)
                && Objects.equal(email, that.email)
                && Objects.equal(storeId, that.storeId)
                && Objects.equal(active, that.active)
                && Objects.equal(username, that.username)
                && Objects.equal(password, that.password)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        // return Objects.hashCode(super.hashCode(), staffId, firstName, lastName, addressId, picture, email,
        //         storeId, active, username, password, lastUpdate);
        return Objects.hashCode(super.hashCode(), staffId, firstName, lastName, addressId, email,
                storeId, active, username, password, lastUpdate);
    }
}
