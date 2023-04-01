package com.example.app.model.response;

import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.core.AddressModel;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = HalRelation.Fields.addressList,
        itemRelation = HalRelation.Fields.address)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseModel extends RepresentationModel<AddressResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private AddressModel addressModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AddressResponseModel that = (AddressResponseModel) o;
        return Objects.equal(addressModel, that.addressModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), addressModel);
    }
}
