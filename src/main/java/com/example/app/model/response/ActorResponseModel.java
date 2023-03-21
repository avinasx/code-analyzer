package com.example.app.model.response;

import com.example.app.model.internal.ActorModel;
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
