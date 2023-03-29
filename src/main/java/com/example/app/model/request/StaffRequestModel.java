package com.example.app.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.validation.constraints.Size;
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
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("addressId")
    private Integer addressId;

    // @JsonProperty("picture")
    // private byte[] picture;

    @JsonProperty("email")
    @Size(min = 1, max = 50)
    private String email;

    @JsonProperty("storeId")
    private Integer storeId;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("username")
    @Size(min = 1, max = 16)
    private String username;

    @JsonProperty("password")
    @Size(min = 1, max = 40)
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
