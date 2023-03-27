package com.example.app.model.internal.extra;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;

/**
 * The actor detail model provides a list of films in which actors appear by category.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActorDetailModel {
    /**
     * actor id
     */
    @JsonProperty("actorId")
    private Integer actorId;

    /**
     * actor first name
     */
    @JsonProperty("firstName")
    private String firstName;

    /**
     * actor last name
     */
    @JsonProperty("lastName")
    private String lastName;

    /**
     * a list of films in which the actor appears by category
     */
    @JsonProperty("filmInfo")
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
