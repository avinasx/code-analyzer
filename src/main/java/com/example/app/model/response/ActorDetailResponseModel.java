package com.example.app.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

/**
 * The actor detail model provides a list of films in which actors appear by category.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActorDetailResponseModel extends RepresentationModel<ActorDetailResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * actor id
     */
    @JsonProperty("actorId")
    @JacksonXmlProperty(localName = "actorId")
    private Integer actorId;

    /**
     * actor first name
     */
    @JsonProperty("firstName")
    @JacksonXmlProperty(localName = "firstName")
    private String firstName;

    /**
     * actor last name
     */
    @JsonProperty("lastName")
    @JacksonXmlProperty(localName = "lastName")
    private String lastName;

    /**
     * a list of films in which the actor appears by category
     */
    @JsonProperty("filmInfo")
    @JacksonXmlProperty(localName = "filmInfo")
    private String filmInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActorDetailResponseModel that = (ActorDetailResponseModel) o;
        return Objects.equal(actorId, that.actorId)
                && Objects.equal(firstName, that.firstName)
                && Objects.equal(lastName, that.lastName)
                && Objects.equal(filmInfo, that.filmInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), actorId, firstName, lastName, filmInfo);
    }
}
