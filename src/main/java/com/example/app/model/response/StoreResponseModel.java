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
public class StoreResponseModel extends RepresentationModel<StoreResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("storeId")
    @JacksonXmlProperty(localName = "storeId")
    private Integer storeId;

    @JsonProperty("managerStaffId")
    @JacksonXmlProperty(localName = "managerStaffId")
    private Integer managerStaffId;

    @JsonProperty("addressId")
    @JacksonXmlProperty(localName = "addressId")
    private Integer addressId;

    @JsonProperty("lastUpdate")
    @JacksonXmlProperty(localName = "lastUpdate")
    private LocalDateTime lastUpdate;
}
