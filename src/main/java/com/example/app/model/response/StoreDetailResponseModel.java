package com.example.app.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailResponseModel extends RepresentationModel<StoreDetailResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @JacksonXmlProperty(localName = "id")
    private Integer id;

    @JsonProperty("store")
    @JacksonXmlProperty(localName = "store")
    private String store;

    @JsonProperty("manager")
    @JacksonXmlProperty(localName = "manager")
    private String manager;

    @JsonProperty("address")
    @JacksonXmlProperty(localName = "address")
    private String address;
}
