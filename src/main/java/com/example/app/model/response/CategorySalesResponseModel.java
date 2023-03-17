package com.example.app.model.response;

import com.example.app.model.enumeration.Category;
import com.example.app.model.mapping.CategoryConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.Convert;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The sales by category model provides the data for the total sales by film category.
 */
@Relation(collectionRelation = "categorySales", itemRelation = "categorySales")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategorySalesResponseModel extends RepresentationModel<CategorySalesResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * category
     */
    @JsonProperty("category")
    @JacksonXmlProperty(localName = "category")
    @Convert(converter = CategoryConverter.class)
    private Category category;

    /**
     * total sales
     */
    @JsonProperty("totalSales")
    @JacksonXmlProperty(localName = "totalSales")
    private BigDecimal totalSales;
}
