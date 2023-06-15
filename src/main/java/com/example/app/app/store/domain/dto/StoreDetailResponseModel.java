package com.example.app.app.store.domain.dto;

import com.example.app.app.staff.domain.dto.StoreDetailsModel;
import com.example.app.common.constant.HalRelation;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = HalRelation.Fields.storeDetailsList,
        itemRelation = HalRelation.Fields.storeDetails)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailResponseModel extends RepresentationModel<StoreDetailResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private StoreDetailsModel storeDetailsModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StoreDetailResponseModel that = (StoreDetailResponseModel) o;
        return Objects.equal(storeDetailsModel, that.storeDetailsModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), storeDetailsModel);
    }
}
