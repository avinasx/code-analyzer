package com.example.app.model.response;

import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.extra.CustomerDetailsModel;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = HalRelation.Fields.customerDetailsList,
        itemRelation = HalRelation.Fields.customerDetails)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailResponseModel extends RepresentationModel<CustomerDetailResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private CustomerDetailsModel customerDetailsModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomerDetailResponseModel that = (CustomerDetailResponseModel) o;
        return Objects.equal(customerDetailsModel, that.customerDetailsModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), customerDetailsModel);
    }
}
