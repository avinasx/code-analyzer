package com.example.app.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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
}
