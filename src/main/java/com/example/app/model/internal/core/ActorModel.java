package com.example.app.model.internal.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActorModel {
    @JsonProperty("actorId")
    private Integer actorId;

    @JsonUnwrapped
    private FullName fullName;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("filmActorsByActorId")
    @ToString.Exclude
    private Collection<FilmActorModel> filmActorsByActorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorModel that = (ActorModel) o;
        return Objects.equal(actorId, that.actorId)
                && Objects.equal(fullName, that.fullName)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actorId, fullName, lastUpdate);
    }
}
