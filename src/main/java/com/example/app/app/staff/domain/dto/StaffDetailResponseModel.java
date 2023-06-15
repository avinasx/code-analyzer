package com.example.app.app.staff.domain.dto;

import com.example.app.common.constant.HalRelation;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = HalRelation.Fields.staffDetailsList,
        itemRelation = HalRelation.Fields.staffDetails)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffDetailResponseModel extends RepresentationModel<StaffDetailResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private StaffDetailsModel staffDetailsModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StaffDetailResponseModel that = (StaffDetailResponseModel) o;
        return Objects.equal(staffDetailsModel, that.staffDetailsModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), staffDetailsModel);
    }
}
