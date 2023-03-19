package com.example.app.model.internal;

import com.google.common.base.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActorDetailModel {
    private Integer actorId;
    private String firstName;
    private String lastName;
    private String filmInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorDetailModel that = (ActorDetailModel) o;
        return Objects.equal(actorId, that.actorId)
                && Objects.equal(firstName, that.firstName)
                && Objects.equal(lastName, that.lastName)
                && Objects.equal(filmInfo, that.filmInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actorId, firstName, lastName, filmInfo);
    }
}
