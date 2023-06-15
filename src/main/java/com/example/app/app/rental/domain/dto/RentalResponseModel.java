package com.example.app.app.rental.domain.dto;

import com.example.app.common.constant.HalRelation;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = HalRelation.Fields.rentalList,
        itemRelation = HalRelation.Fields.rental)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalResponseModel extends RepresentationModel<RentalResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private RentalModel rentalModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RentalResponseModel that = (RentalResponseModel) o;
        return Objects.equal(rentalModel, that.rentalModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), rentalModel);
    }
}
