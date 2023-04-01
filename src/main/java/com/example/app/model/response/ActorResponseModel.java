package com.example.app.model.response;

import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.core.ActorModel;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = HalRelation.Fields.actorList,
        itemRelation = HalRelation.Fields.actor)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActorResponseModel extends RepresentationModel<ActorResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private ActorModel actorModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActorResponseModel that = (ActorResponseModel) o;
        return Objects.equal(actorModel, that.actorModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), actorModel);
    }
}
