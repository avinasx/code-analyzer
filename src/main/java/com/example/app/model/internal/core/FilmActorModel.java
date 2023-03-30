package com.example.app.model.internal.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmActorModel {
    @JsonProperty("actorId")
    private Integer actorId;

    @JsonProperty("filmId")
    private Integer filmId;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("actorByActorId")
    @ToString.Exclude
    private ActorModel actorByActorId;

    @JsonIgnore
    @JsonProperty("filmByFilmId")
    @ToString.Exclude
    private FilmModel filmByFilmId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmActorModel that = (FilmActorModel) o;
        return Objects.equal(actorId, that.actorId)
                && Objects.equal(filmId, that.filmId)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actorId, filmId, lastUpdate);
    }
}
