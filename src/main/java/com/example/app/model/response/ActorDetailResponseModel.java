package com.example.app.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
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
}
