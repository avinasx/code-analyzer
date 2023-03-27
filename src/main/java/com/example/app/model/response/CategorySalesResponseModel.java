package com.example.app.model.response;

import com.example.app.model.internal.extra.CategorySalesModel;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = "categorySales", itemRelation = "categorySales")
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
