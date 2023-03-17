package com.example.app.model.response.reserved;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseModel extends RepresentationModel<InventoryResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("inventoryId")
    @JacksonXmlProperty(localName = "inventoryId")
    private Integer inventoryId;

    @JsonProperty("filmId")
    @JacksonXmlProperty(localName = "filmId")
    private Integer filmId;

    @JsonProperty("storeId")
    @JacksonXmlProperty(localName = "storeId")
    private Integer storeId;

    @JsonProperty("lastUpdate")
    @JacksonXmlProperty(localName = "lastUpdate")
    private LocalDateTime lastUpdate;
}
