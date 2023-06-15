package com.example.app.app.store.domain.dto;

import com.example.app.app.catalog.domain.dto.CategorySalesModel;
import com.example.app.common.constant.HalRelation;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = HalRelation.Fields.categorySalesList,
        itemRelation = HalRelation.Fields.categorySales)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySalesResponseModel extends RepresentationModel<CategorySalesResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private CategorySalesModel categorySalesModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CategorySalesResponseModel that = (CategorySalesResponseModel) o;
        return Objects.equal(categorySalesModel, that.categorySalesModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), categorySalesModel);
    }
}
