package com.example.app.model.response;

import com.example.app.model.internal.extra.CustomerDetailModel;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

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
    private CustomerDetailModel customerDetailModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomerDetailResponseModel that = (CustomerDetailResponseModel) o;
        return Objects.equal(customerDetailModel, that.customerDetailModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), customerDetailModel);
    }
}
