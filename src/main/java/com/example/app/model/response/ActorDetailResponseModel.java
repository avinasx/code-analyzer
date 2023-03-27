package com.example.app.model.response;

import com.example.app.model.internal.extra.ActorDetailModel;
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
public class ActorDetailResponseModel extends RepresentationModel<ActorDetailResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private ActorDetailModel actorDetailModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActorDetailResponseModel that = (ActorDetailResponseModel) o;
        return Objects.equal(actorDetailModel, that.actorDetailModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), actorDetailModel);
    }
}
