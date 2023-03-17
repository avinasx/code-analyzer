package com.example.app.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The sales by store model provides the data for the total sales by store.
 */
@Relation(collectionRelation = "storeSales", itemRelation = "storeSales")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreSalesResponseModel extends RepresentationModel<StoreSalesResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * store
     */
    @JsonProperty("store")
    @JacksonXmlProperty(localName = "store")
    private String store;

    /**
     * store manager
     */
    @JsonProperty("manager")
    @JacksonXmlProperty(localName = "manager")
    private String manager;

    /**
     * total sales
     */
    @JsonProperty("totalSales")
    @JacksonXmlProperty(localName = "totalSales")
    private BigDecimal totalSales;
}
