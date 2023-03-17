package com.example.app.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActorResponseModel extends RepresentationModel<ActorResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("actorId")
    @JacksonXmlProperty(localName = "actorId")
    private Integer actorId;

    @JsonProperty("firstName")
    @JacksonXmlProperty(localName = "firstName")
    private String firstName;

    @JsonProperty("lastName")
    @JacksonXmlProperty(localName = "lastName")
    private String lastName;

    @JsonProperty("lastUpdate")
    @JacksonXmlProperty(localName = "lastUpdate")
    private LocalDateTime lastUpdate;
}
